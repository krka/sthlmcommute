package se.krka.travelopt;


import java.util.*;

public class TravelOpt {
	private final PriceStructure priceStructure;

	public TravelOpt(PriceStructure priceStructure) {
		this.priceStructure = priceStructure;
	}

	public TravelResult findOptimum(TravelPlan travelPlan) {
        if (travelPlan.getDates().isEmpty()) {
            return new TravelResult(travelPlan.getLocale(), Collections.EMPTY_LIST);
        }

        if (travelPlan.getNumDays() > 2*365) {
            throw new IllegalArgumentException(travelPlan.getLocale().tooLongPeriodError());
        }

        List<Purchase> purchases = new ArrayList<Purchase>();

        Date firstDay = null;
        Date lastDate = null;

        Date looper = null;

        for (TravelPlanDate travelPlanDate : travelPlan.getDates()) {
            Date date =  travelPlanDate.getDate();

            if (lastDate != null) {
                looper = lastDate;
                looper = Util.plusDays(looper, 1);
                while (looper.before(date)) {
                    handle(purchases, 0);
                    looper = Util.plusDays(looper, 1);
                }
            } else {
                firstDay = date;
            }
            handle(purchases, travelPlanDate.getNumTickets());
            lastDate = date;
        }

        if (firstDay == null) {
            return new TravelResult(travelPlan.getLocale(), Collections.EMPTY_LIST);
        }

        looper = lastDate;
        looper = Util.plusDays(looper, 1);

        looper = extendPeriod(purchases, looper);


        optimizedExtendPeriod(purchases, travelPlan, firstDay);

        Stack<Purchase> stack = new Stack<Purchase>();
        Purchase current = lastPurchase(purchases);
        while (current != Purchase.EMPTY) {
            stack.add(current);
            current = current.parent;
        }
        List<Ticket> tickets = new ArrayList<Ticket>();
        while (!stack.isEmpty()) {
            Purchase purchase = stack.pop();
            Date startDate = Util.plusDays(firstDay, purchase.startAt);
            Money cost = purchase.totalCost.subtract(purchase.parent.totalCost);
            TicketType ticketType = purchase.ticketType;
            tickets.add(new Ticket(travelPlan.getLocale(), cost, ticketType, startDate));
        }
        return new TravelResult(travelPlan.getLocale(), tickets);
    }

	private void optimizedExtendPeriod(List<Purchase> purchases, TravelPlan travelPlan, Date firstDay) {
		Date extensionStart = travelPlan.getExtensionStart();
		int i = purchases.size() - 1;
		double bestScore = Double.MAX_VALUE;
		Purchase bestPurchase = null;
		while (true) {
			Purchase purchase = getPurchase(i, purchases);
			Date startDate = Util.plusDays(firstDay, i);
			if (startDate.before(extensionStart)) {
				break;
			}

            // Don't count purchases made after the extension
            if (Util.plusDays(firstDay, purchase.startAt).before(extensionStart)) {
                double score = purchase.totalCost.getValue() / (i + 1);
                if (score <= bestScore) {
                    bestScore = score;
                    bestPurchase = purchase;
                }
            }

			i--;
		}
		if (bestPurchase == null) {
			return;
		}
		for (i = purchases.size() - 1; i >= 0; i--) {
			Purchase purchase = getPurchase(i, purchases);
			if (purchase == bestPurchase) {
				break;
			}
			purchases.remove(i);
		}
	}

	private Date extendPeriod(List<Purchase> purchases, Date looper) {
		Purchase current = lastPurchase(purchases);
		Money cost = current.totalCost;
		while (current.totalCost.equals(cost)) {
            looper = Util.plusDays(looper, 1);
			handle(purchases, 1);
			current = lastPurchase(purchases);
		}
		purchases.remove(purchases.size() - 1);
        return looper;
	}

	private Purchase lastPurchase(List<Purchase> purchases) {
		return purchases.get(purchases.size() - 1);
	}

	private void handle(List<Purchase> purchases, int numTickets) {
		int i = purchases.size();
		Purchase best = null;
		for (TicketType ticketType : priceStructure.getTicketTypes()) {
			int days = ticketType.numberOfDays();
			Money cost = ticketType.cost(numTickets);

			Purchase prev = getPurchase(i - days, purchases);

			Purchase newBest;
			if (cost.equals(Money.ZERO)) {
				newBest = prev;
			} else {
				newBest = new Purchase(1 + i - days, prev, cost.add(prev.totalCost), ticketType);
			}
			if (best == null || newBest.isBetterThan(best)) {
				best = newBest;
			}
		}
		if (best == null) {
			throw new RuntimeException("Unknown algorithm bug.");
		}
		purchases.add(best);
	}

	private Purchase getPurchase(int i, List<Purchase> purchases) {
		if (i < 0) {
			return Purchase.EMPTY;
		}
		return purchases.get(i);
	}

	static class Purchase {
		private final static Purchase EMPTY = new Purchase(0, null, Money.ZERO, null);

		private int startAt;
		private final Purchase parent;
		private final Money totalCost;
		private final TicketType ticketType;

		private Purchase(int startAt, Purchase parent, Money totalCost, TicketType ticketType) {
			this.startAt = startAt;
			this.parent = parent;
			this.totalCost = totalCost;
			this.ticketType = ticketType;
		}

		public boolean isBetterThan(Purchase other) {
            int cmp = totalCost.compareTo(other.totalCost);
            if (cmp != 0) {
                return cmp < 0;
            }
			if (startAt > other.startAt) {
				return true;
			}
			if (startAt < other.startAt) {
				return false;
			}
			return false;
		}

		@Override
		public String toString() {
			return "Purchase{" +
					"startAt=" + startAt +
					", totalCost=" + totalCost +
					", ticketType=" + ticketType +
					'}';
		}
	}
}
