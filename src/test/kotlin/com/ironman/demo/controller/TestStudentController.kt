package com.ironman.demo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ironman.demo.data.entity.Student
import com.ironman.demo.service.Impl.StudentServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/10/2
 */
@WebMvcTest(StudentController::class)
class TestStudentController() {

    @MockBean
    lateinit var studentServiceImpl: StudentServiceImpl

    @Autowired
    lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @Test
    fun shouldGetAllStudentWhenCallMethod() {
        val expectedResult : MutableList<Student> = mutableListOf<Student>()
        expectedResult.add(Student(1, "Devin", "devin@gmail.com"))
        expectedResult.add(Student(2, "Eric", "eric@gmail.com"))
        given(studentServiceImpl.findAllStudent()).willReturn(expectedResult)

        mockMvc.perform(
                get("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(objectMapper.writeValueAsString(expectedResult)))
    }

    @Test
    fun shouldGetOneStudentWhenCallMethodById() {
        val expectedResult : MutableList<Student> = mutableListOf<Student>()
        expectedResult.add(Student(1, "Devin", "devin@gmail.com"))
        given(studentServiceImpl.findByStudentName("Devin")).willReturn(expectedResult)

        mockMvc.perform(
                post("/api/students/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "Devin")
        ).andExpect(status().isOk)
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(objectMapper.writeValueAsString(expectedResult)))
    }

    @Test
    fun shouldGetNewStudentWhenCallMethodByStudent() {
        val expectedResult = Student( 1, "Devin", "devin@gmail.com")
        val requestParameter = Student( name = "Devin", email = "devin@gmail.com")
        given(studentServiceImpl.addStudent(requestParameter)).willReturn(expectedResult)

        mockMvc.perform(
                post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestParameter))
        ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResult)))
    }

    @Test
    fun shouldUpdatedStudentWhenCallMethodByStudent() {
        val expectedResult = Student(1, "Devin", "devin@gmail.com")
        val requestParameter = Student(1, "Eric", "eric@gmail.com")
        given(studentServiceImpl.findByStudentId(1)).willReturn(requestParameter)
        given(studentServiceImpl.updateStudent(requestParameter)).willReturn(expectedResult)

        mockMvc.perform(
                put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestParameter))
        ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResult)))
    }

    @Test
    fun shouldUpdatedEmailWhenCallMethodByStudent() {
        val expectedResult = Student(1, "Devin", "devin@gmail.com")
        val requestParameter = Student(1, "Devin", "test@gmail.com")
        given(studentServiceImpl.findByStudentId(1)).willReturn(requestParameter)
        given(studentServiceImpl.updateStudentEmail(requestParameter)).willReturn(expectedResult)

        mockMvc.perform(
                patch("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestParameter))
        ).andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(objectMapper.writeValueAsString(expectedResult)))
    }

    @Test
    fun shouldGetIsNotContentStatusWhenDeleteSuccess() {
        val expectedResult = true
        given(studentServiceImpl.deleteStudent(1)).willReturn(expectedResult)

        mockMvc.perform(
                delete("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent)
    }

    @Test
    fun shouldGetBadRequestStatusWhenDeleteFailed() {
        val expectedResult = false
        given(studentServiceImpl.deleteStudent(1)).willReturn(expectedResult)

        mockMvc.perform(
                delete("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest)
    }
}