package co.com.sofka.mongo.data;

import co.com.sofka.model.patient.generic.DomainEvent;
import co.com.sofka.serializer.JSONMapper;

import java.util.Date;

public class StoredEvent {
    private String eventBody;
    private String aggregateRootId;
    private Date occurredOn;
    private String typeName;

    public StoredEvent() {
    }
    public StoredEvent(String typeName, Date occurredOn, String eventBody) {
        this.setEventBody(eventBody);
        this.setOccurredOn(occurredOn);
        this.setTypeName(typeName);
    }
    public static String wrapEvent(DomainEvent domainEvent, JSONMapper eventSerializer){
        return eventSerializer.writeToJson(domainEvent);
    }

    public DomainEvent deserializeEvent(JSONMapper eventSerializer) {
        try{
            System.out.println("event body: "+this.getEventBody());
            System.out.println("getTypeName" + this.getTypeName());
            return (DomainEvent) eventSerializer
                    .readFromJson(this.getEventBody(), Class.forName(this.getTypeName()));
        }catch (ClassNotFoundException e){
            return null;
        }catch (NullPointerException e){
            System.out.println("NullPointereeeee");
            return null;
        }

    }

    public interface EventSerializer {
        <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType);

        String serialize(DomainEvent object);
    }

    public String getEventBody() {
        return eventBody;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    public String getAggregateRootId() {
        return aggregateRootId;
    }

    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
