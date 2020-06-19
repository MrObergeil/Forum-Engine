package at.fhj.ima.forumengine.forumengine.repository

import at.fhj.ima.forumengine.forumengine.entity.Forum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface ForumRepository : JpaRepository<Forum, Int> {

    @Query("FROM Forum WHERE name = :search")
    fun findByName(@Param("search") search: String): List<Forum>

    @Transactional
    @Modifying
    @Query("UPDATE Forum SET name = :name, description = :description WHERE forumId = :id")
    fun updateById(@Param("id") forumId: Int, @Param("name") name: String, @Param("description") description: String)
}