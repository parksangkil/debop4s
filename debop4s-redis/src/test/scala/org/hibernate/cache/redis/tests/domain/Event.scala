package org.hibernate.cache.redis.tests.domain

import java.util
import java.util.Date
import javax.persistence._
import org.hibernate.annotations.CacheConcurrencyStrategy

@Entity
@org.hibernate.annotations.Cache(region = "account", usage = CacheConcurrencyStrategy.READ_WRITE)
@Access(AccessType.FIELD)
class Event extends Serializable {

    @Id
    @GeneratedValue
    var id: Long = _

    var title: String = _

    @Temporal(TemporalType.TIMESTAMP)
    var date: Date = _

    @ManyToMany(cascade = Array(CascadeType.ALL))
    var participants: util.Set[Person] = new util.HashSet[Person]()

    @ManyToOne
    @JoinColumn(name = "organizerId")
    var organizer: Person = _

    def addParticipant(person: Person) {
        participants.add(person)
        person.events.add(this)
    }

    def removeParticipants(person: Person) {
        participants.remove(person)
        person.events.remove(this)
    }

    override def toString(): String = title + ": " + date

}
