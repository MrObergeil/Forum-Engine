package at.fhj.ima.forumengine.forumengine.repository

import at.fhj.ima.forumengine.forumengine.entity.Answer
import at.fhj.ima.forumengine.forumengine.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

interface AnswerRepository : JpaRepository<Answer, Int> {

    @Query("FROM Answer WHERE question.questId = :questId")
    fun findByQuestion(@Param("questId") questId: Int): List<Answer>

    @Transactional
    @Modifying
    @Query("UPDATE Answer SET text = :text WHERE answId = :id")
    fun updateById(@Param("id") id: Int, @Param("text") text: String)
}