package at.fhj.ima.forumengine.forumengine.controller

import at.fhj.ima.forumengine.forumengine.entity.Answer
import at.fhj.ima.forumengine.forumengine.entity.Forum
import at.fhj.ima.forumengine.forumengine.entity.Question
import at.fhj.ima.forumengine.forumengine.repository.AnswerRepository
import at.fhj.ima.forumengine.forumengine.repository.ForumRepository
import at.fhj.ima.forumengine.forumengine.repository.QuestionRepository
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import javax.validation.Valid

@Controller
class ForumController(
        val forumRepository: ForumRepository,
        val questionRepository: QuestionRepository,
        val answerRepository: AnswerRepository
) {

    //Forums
    @RequestMapping("/forums", method = [RequestMethod.GET])
    fun forums(model: Model): String {
        model["forums"] = forumRepository.findAll()
        return "forums"
    }

    @RequestMapping("/forum", method = [RequestMethod.GET])
    fun showForum(model: Model, @RequestParam(required = true) id: Int): String {
        model["questions"] =  questionRepository.findByForumId(id)
        val forum: Optional<Forum> = forumRepository.findById(id)
        model["forum"] = forum.get()
        return "forum"
        }

    @RequestMapping("/newforum", method = [RequestMethod.GET])
    fun newOrEditForum(model: Model, @RequestParam(required = false) forumId: Int?): String {
        if (forumId != null) {
            val forum = forumRepository.findById(forumId)
            model["forum"] = forum.get()
        }
            return "newforum"
    }

    @RequestMapping("/createforum", method = [RequestMethod.POST])
    fun createForum(@ModelAttribute("forum") @Valid forum: Forum,
                    @RequestParam("forumId", required = false) forumId: Int?,
                    bindingResult: BindingResult, model: Model): String {
        if (bindingResult.hasErrors()) {
            return "forums"
        } else {
            try {
                val alreadyExists: Boolean = forumRepository.existsById(forumId?:0)
                if (alreadyExists) {
                    //We use !! here because if forumId == null we cannot reach this block
                    forumRepository.updateById(forumId!!, forum.name, forum.description)
                } else {
                    forumRepository.save(forum)
                }
            } catch (dive: DataIntegrityViolationException) {
                if (dive.message.orEmpty().contains("name")) {
                    bindingResult.rejectValue("name", "name.alreadyInUse", "Name already in use.")
                    return "newforum"
                } else {
                    throw dive
                }
            }
        }
        return "redirect:/forum?id=" + forum.forumId.toString()
    }

    @RequestMapping("/deleteforum", method = [RequestMethod.GET])
    fun deleteForum(@RequestParam("forumId", required = true) forumId: Int): String {
        forumRepository.deleteById(forumId)
        return "redirect:forums"
    }

    //Questions
    @RequestMapping("/question", method = [RequestMethod.GET])
    fun showQuestion(model: Model, @RequestParam(required = true) forumId: Int, @RequestParam(required = true) questId: Int): String {
        val forum: Optional<Forum> = forumRepository.findById(forumId)
        val question: Optional<Question> = questionRepository.findById(questId)
        model["forum"] = forum.get()
        model["question"] = question.get()
        model["answers"] = answerRepository.findByQuestion(questId)
        return "question"
    }

    @RequestMapping("/newquestion", method=[RequestMethod.GET])
    fun newOrEditQuestion(model:Model,
                          @RequestParam(required = true) forumId: Int,
                          @RequestParam(required = false) questId: Int?): String {
        val forum = forumRepository.findById(forumId)
        model["forum"] = forum.get()
        if (questId != null) {
            model["question"] = questionRepository.findById(questId).get()
        }
        return "newquestion"
    }

    @RequestMapping("/createquestion", method = [RequestMethod.POST])
    fun createQuestion(@ModelAttribute("question") @Valid question: Question,
                       @RequestParam(required = true) forumId: Int,
                       @RequestParam(required = false) questId: Int?,
                       bindingResult: BindingResult,
                       model: Model): String {
        if (bindingResult.hasErrors()) {
            return "forums"
        } else {
            val forumopt = forumRepository.findById(forumId)
            var forum: Forum
            try {
                forum = forumopt.get()
                question.forum = forum
                val alreadyExists: Boolean = questionRepository.existsById(questId?:0)
                if (alreadyExists) {
                    //We use !! here because if forumId == null we cannot reach this block
                    questionRepository.updateById(questId!!, question.title, question.text)
                } else {
                    questionRepository.save(question)
                }
            } catch (dive: DataIntegrityViolationException) {
                return "newforum"
            }
            forum = forumopt.get()
            return String.format("redirect:/question?forumId=%d&questId=%s", forum.forumId, question.questId)
        }
    }

    @RequestMapping("/deletequestion", method = [RequestMethod.GET])
    fun deleteQuestion(@RequestParam("forumId", required = false) forumId: Int?,
                       @RequestParam("questId", required = true) questId: Int): String {
        questionRepository.deleteById(questId)
        return if (forumId != null) {
            "redirect:/forum?forumId=${forumId}"
        } else {
            "redirect:/forums"
        }
    }
    //Answers
    @RequestMapping("/createanswer", method = [RequestMethod.POST])
    fun createAnswer(@ModelAttribute("answer") @Valid answer: Answer,
                     @RequestParam(required = true) forumId: Int,
                     @RequestParam(required = true) questId: Int,
                     bindingResult: BindingResult,
                     model: Model): String {
        if (bindingResult.hasErrors()) {
            return "forums"
        } else {
            val forum: Forum
            val question: Question
            val forumOpt = forumRepository.findById(forumId)
            forum = forumOpt.get()
            val questionOpt = questionRepository.findById(questId)
            question = questionOpt.get()
            var ans = answer
            ans.question = question
            answerRepository.save(ans)
            return String.format("redirect:/question?forumId=%d&questId=%s", forum.forumId, question.questId)

        }
    }

    @RequestMapping("/editanswer", method = [RequestMethod.POST])
    fun editAnswer(@ModelAttribute("answer") @Valid answer:Answer,
                   @RequestParam(required = true) ansId: Int,
                   bindingResult: BindingResult,
                   model: Model): String {
        answerRepository.updateById(ansId, answer.text)
        return String.format("redirect:/question?forumId=%d&questId=%s", answer.question?.forum?.forumId, answer.question?.questId)
    }

    @RequestMapping("/deleteanswer", method = [RequestMethod.GET])
    fun deleteAnswer(@RequestParam("forumId", required = false) forumId: Int?,
                     @RequestParam("questId", required = false) questId: Int?,
                     @RequestParam("answId", required = true) answId: Int): String {
        answerRepository.deleteById(answId)
        return if (forumId != null) {
            if (questId != null) {
                "redirect:/question?forumId=${forumId}&questId=${questId}"
            } else {
                "redirect:/forum?forumId=${forumId}"
            }
        } else {
            "redirect:/forums"
        }
    }
}