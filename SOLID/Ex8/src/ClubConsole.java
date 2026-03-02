public class ClubConsole {
    private final BudgetLedger ledger;
    private final MinutesBook minutes;
    private final EventPlanner events;

    public ClubConsole(BudgetLedger ledger, MinutesBook minutes, EventPlanner events) {
        this.ledger = ledger; this.minutes = minutes; this.events = events;
    }

    public void run() {
        Treasurer treasurer = new TreasurerTool(ledger);
        Secretary secretary = new SecretaryTool(minutes);
        EventLead lead = new EventLeadTool(events);
        PublicityLead publicityLead = new PublicityLeadTool(events);

        treasurer.addIncome(5000, "sponsor");
        secretary.addMinutes("Meeting at 5pm");
        lead.createEvent("HackNight", 2000);
        publicityLead.createEvent("Party", 3000);

        System.out.println("Summary: ledgerBalance=" + ledger.balanceInt() + ", minutes=" + minutes.count() + ", events=" + lead.getEventsCount());
    }
}
