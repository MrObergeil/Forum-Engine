package at.fhj.ima.forumengine.forumengine.entity

import com.sun.scenario.effect.Identity
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table
class Question(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var questId: Int? = null,
        @Column
        var title: String = "",
        @Column(columnDefinition = "LONGTEXT")
        var text: String = "",
        var postedOn: LocalDate? = LocalDate.now(),
        var hochwaehlis: Int = 0,
        var runterwaehlis: Int = 0,
        @ManyToOne
        @JoinColumn(name = "forumId")
        var forum: Forum? = null,
        @OneToMany(mappedBy = "question", orphanRemoval = true, cascade = [CascadeType.ALL])
        var answers: MutableList<Answer> = mutableListOf()
) {
        override fun toString(): String {
                return "Question(questId=$questId, title='$title', text='$text', postedOn=$postedOn, hochwaehlis=$hochwaehlis, runterwaehlis=$runterwaehlis, forum=$forum, answers=$answers)"
        }

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Question

                if (questId != other.questId) return false
                if (title != other.title) return false
                if (text != other.text) return false
                if (postedOn != other.postedOn) return false
                if (hochwaehlis != other.hochwaehlis) return false
                if (runterwaehlis != other.runterwaehlis) return false
                if (forum != other.forum) return false
                if (answers != other.answers) return false

                return true
        }

        override fun hashCode(): Int {
                var result = questId ?: 0
                result = 31 * result + title.hashCode()
                result = 31 * result + text.hashCode()
                result = 31 * result + (postedOn?.hashCode() ?: 0)
                result = 31 * result + hochwaehlis
                result = 31 * result + runterwaehlis
                result = 31 * result + (forum?.hashCode() ?: 0)
                result = 31 * result + answers.hashCode()
                return result
        }


}