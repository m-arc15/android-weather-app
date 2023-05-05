# Use Architectural Decision Records (ADRs)

* Status: Accepted
* Deciders: Marcin
* Date: 2023-05-05

## Context and Problem Statement

Iterative and incremental software engineering processes (like Agile practices) require flexible approach to capture any decisions and theirs rationales in a structured way. An architectural decision record (ADR) is a document that describes a choice the team makes about a significant aspect of the software architecture they're planning to build. Each ADR describes the architectural decision, its context, and its consequences. ADRs have states and therefore follow a lifecycle.

**Which format and structure should these ADRs follow?**

## Considered Options

* [MADR](https://adr.github.io/madr/)
* [Michael Nygard's template](http://thinkrelevance.com/blog/2011/11/15/documenting-architecture-decisions)
* [Amazon ADR example](https://docs.aws.amazon.com/prescriptive-guidance/latest/architectural-decision-records/appendix.html)
* [ADR examples for software planning, IT leadership, and template documentation](https://github.com/joelparkerhenderson/architecture-decision-record)

## Decision Outcome

Chosen option: "Markdown Any Decision Records", because:

* The MADR format is lean and fits agile development style
* The MADR structure is comprehensive and easy to maintain

The workflow will be:

| Step | Status | Description |
| --- | --- | --- |
| 1 | Proposed | A developer creates an ADR document presenting an approach for a particular question or problem. |
| 2 | Proposed | The developers and steering group discuss the ADR. During this period, the ADR should be updated to reflect additional context, concerns raised, and proposed changes. |
| 3 | Accepted or Rejected | Once consensus is reached, ADR can be transitioned to either an "accepted" or "rejected" state. |
| 4 | Deprecated | If a decision is revisited and a different conclusion is reached, a new ADR should be created documenting the context and rationale for the change. The new ADR should reference the old one, and once the new one is accepted, the old one should (in its "status" section) be updated to point to the new one. The old ADR should not be removed or otherwise modified except for the annotation to the new ADR. |

**Only after an ADR is accepted** - implementing code should be committed to the main branch of the relevant project / module.

## Consequences

* Good, because developers must write an ADR and submit it for review before selecting an approach to solution decision.
* Good, because we will have a concrete artifact to focus discussion before finalizing decision.
* Good, because we will have a persistent record of why the system is the way it is (living documentation).

## More Information

* [ADR template](template/adr-template.md)
