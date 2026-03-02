public class PublicityLeadTool extends EventLeadTool implements PublicityLead {
    public PublicityLeadTool(EventPlanner events) {
        super(events);
    }

    @Override public void createEvent(String name, double budget) { super.createEvent(name, budget); }
    @Override public int getEventsCount() { return super.getEventsCount(); }
}
