package at.fhj.ima.forumengine.forumengine.repository

import at.fhj.ima.forumengine.forumengine.entity.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface QuestionRepository : JpaRepository<Question, Int> {

    @Query("FROM Question WHERE forum.forumId = :id")
    fun findByForumId(@Param("id") id: Int): List<Question>

    @Transactional
    @Modifying
    @Query("UPDATE Question SET title = :title, text = :text WHERE questId = :id")
    fun updateById(@Param("id") id: Int,
                   @Param("title") title: String,
                   @Param("text") text: String)
}