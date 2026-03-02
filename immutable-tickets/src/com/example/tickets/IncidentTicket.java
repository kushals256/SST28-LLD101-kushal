package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - mutable fields
 * - multiple constructors
 * - public setters
 * - tags list can be modified from outside
 * - validation is scattered elsewhere
 *
 * TODO (student): refactor to immutable + Builder.
 */
public class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final TicketPriorityEnum priority; // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags; // mutable leak
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes; // optional
    private final String source; // e.g. "CLI", "WEBHOOK", "EMAIL"

    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority != null ? TicketPriorityEnum.valueOf(b.priority) : null;
        this.tags = b.tags != null ? new ArrayList<>(b.tags) : new ArrayList<>();
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority != null ? priority.name() : null;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public boolean isCustomerVisible() {
        return customerVisible;
    }

    public Integer getSlaMinutes() {
        return slaMinutes;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    static class Builder {
        String id;
        String reporterEmail;
        String title;
        String description;
        String priority;
        List<String> tags;
        String assigneeEmail;
        boolean customerVisible;
        Integer slaMinutes;
        String source;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder reporterEmail(String reporterEmail) {
            this.reporterEmail = reporterEmail;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }

        public Builder assigneeEmail(String assigneeEmail) {
            this.assigneeEmail = assigneeEmail;
            return this;
        }

        public Builder customerVisible(boolean customerVisible) {
            this.customerVisible = customerVisible;
            return this;
        }

        public Builder slaMinutes(Integer slaMinutes) {
            this.slaMinutes = slaMinutes;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public IncidentTicket build() {

            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireTitle(title);
            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL", null);
            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");
            return new IncidentTicket(this);
        }

        public Builder from(IncidentTicket t) {
            return new Builder()
                    .id(t.getId())
                    .reporterEmail(t.getReporterEmail())
                    .title(t.getTitle())
                    .description(t.getDescription())
                    .priority(t.getPriority())
                    .tags(t.getTags())
                    .assigneeEmail(t.getAssigneeEmail())
                    .customerVisible(t.isCustomerVisible())
                    .slaMinutes(t.getSlaMinutes())
                    .source(t.getSource());
        }

    }
}
