package se.krka.travelopt;


import java.util.*;

public class TravelOpt {
	private final PriceStructure priceStructure;

	public TravelOpt(PriceStructure priceStructure) {
		this.priceStructure = priceStructure;
	}

	public TravelResult findOptimum(TravelPlan travelPlan) {
        if (travelPlan.getDates().isEmpty()) {
            return emptyResult(travelPlan);
        }

        if (travelPlan.getNumDays() > 1000) {
            throw new IllegalArgumentException();
        }

        List<Purchase> purchases = new ArrayList<Purchase>();

        int firstDay = 0;
        int lastDay = 0;

        for (TravelPlanDate travelPlanDate : travelPlan.getDates()) {
            int  dayOrdinal =  travelPlanDate.getDayOrdinal();
            if (lastDay != 0) {
                // Handle intermediate dates
                for (int i = lastDay + 1; i < dayOrdinal; i++) {
                    handle(purchases, 0);
                }
            } else {
                firstDay = dayOrdinal;
            }
            handle(purchases, travelPlanDate.getNumCoupons());
            lastDay = dayOrdinal;
        }

        if (firstDay == 0) {
            return emptyResult(travelPlan);
        }

        extendPeriod(purchases);


        optimizedExtendPeriod(purchases, travelPlan, firstDay);

        if (purchases.isEmpty()) {
            return emptyResult(travelPlan);
        }

        Stack<Purchase> stack = new Stack<Purchase>();
        Purchase current = lastPurchase(purchases);
        while (current != Purchase.EMPTY) {
            stack.add(current);
            current = current.parent;
        }
        List<Ticket> tickets = new ArrayList<Ticket>();
        while (!stack.isEmpty()) {
            Purchase purchase = stack.pop();
            int startDate = firstDay + purchase.startAt;
            Money cost = purchase.totalCost.subtract(purchase.parent.totalCost);
            TicketType ticketType = purchase.ticketType;
            int endDate = startDate + ticketType.numberOfDays() - 1;
            int count = purchase.count;
            tickets.add(new Ticket(travelPlan.getLocale(), cost, ticketType, startDate, endDate, count));
        }
        return new TravelResult(travelPlan.getLocale(), tickets);
    }

    private TravelResult emptyResult(TravelPlan travelPlan) {
        return new TravelResult(travelPlan.getLocale(), Collections.EMPTY_LIST);
    }

    private void optimizedExtendPeriod(List<Purchase> purchases, TravelPlan travelPlan, int firstDay) {
		int extensionStart = travelPlan.getExtensionStart();
		int i = purchases.size() - 1;
		double bestScore = Double.MAX_VALUE;
		Purchase bestPurchase = null;
        while (true) {
			Purchase purchase = getPurchase(i, purchases);
			int startDate = firstDay + i;

            // Don't count purchases made after the extension
            if (firstDay + purchase.startAt < extensionStart) {
                double score = purchase.totalCost.getValue() / (i + 1);
                if (score <= bestScore) {
                    bestScore = score;
                    bestPurchase = purchase;
                }
            }

            if (startDate < extensionStart) {
                break;
            }

			i--;
		}
		if (bestPurchase == null) {
            i = purchases.size() - 1;
            while (i >= 0) {
                Purchase purchase = getPurchase(i, purchases);
                if (firstDay + purchase.startAt < extensionStart) {
                    break;
                }
                purchases.remove(i);
                i--;
            }
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

	private void extendPeriod(List<Purchase> purchases) {
		Purchase current = lastPurchase(purchases);
		Money cost = current.totalCost;
		while (current.totalCost.equals(cost)) {
			handle(purchases, 1);
			current = lastPurchase(purchases);
		}
		purchases.remove(purchases.size() - 1);
	}

	private Purchase lastPurchase(List<Purchase> purchases) {
		return purchases.get(purchases.size() - 1);
	}

	private void handle(List<Purchase> purchases, int numCoupons) {
		int i = purchases.size();
		Purchase best = null;
		for (TicketType ticketType : priceStructure.getTicketTypes()) {
			int days = ticketType.numberOfDays();
			Money cost = ticketType.cost(numCoupons);
            int count = ticketType.getCount(numCoupons);

            Purchase prev = getPurchase(i - days, purchases);

			Purchase newBest;
			if (cost.equals(Money.ZERO)) {
				newBest = prev;
			} else {
				newBest = new Purchase(1 + i - days, prev, cost.add(prev.totalCost), ticketType, count);
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
		private final static Purchase EMPTY = new Purchase(0, null, Money.ZERO, null, 0);

		private final int startAt;
		private final Purchase parent;
		private final Money totalCost;
		private final TicketType ticketType;
        private final int count;

        private Purchase(int startAt, Purchase parent, Money totalCost, TicketType ticketType, int count) {
			this.startAt = startAt;
			this.parent = parent;
			this.totalCost = totalCost;
			this.ticketType = ticketType;
            this.count = count;
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
