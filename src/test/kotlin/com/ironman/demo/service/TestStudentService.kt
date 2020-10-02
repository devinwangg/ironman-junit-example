package com.ironman.demo.service

import com.ironman.demo.data.dao.StudentDao
import com.ironman.demo.data.entity.Student
import com.ironman.demo.service.Impl.StudentServiceImpl
import org.hibernate.service.spi.ServiceException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.anyInt
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import kotlin.math.exp

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/10/1
 */
@SpringBootTest
class TestStudentService {

    @MockBean
    lateinit var studentDao: StudentDao

    @Autowired
    lateinit var studentServiceImpl: StudentServiceImpl

    @Test
    fun shouldGetAllStudentWhenCallMethod() {
        // Arrange 初始化測試資料與預期結果
        val expectedResult : MutableList<Student> = mutableListOf<Student>()
        expectedResult.add(Student(1, "Devin", "devin@gmail.com"))
        expectedResult.add(Student(2, "Eric", "eric@gmail.com"))
        given(studentDao.findAll()).willReturn(expectedResult)

        // Act 執行測試工作單元，取得實際測試結果
        val actual : MutableList<Student> = studentServiceImpl.findAllStudent()

        // Assert 驗證結果是否符合預期結果
        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldGetOneStudentWhenCallMethodById() {
        val expectedResult = Student(1, "Devin", "devin@gmail.com")
        given(studentDao.findById(1)).willReturn(expectedResult)

        val actual : Student? = studentServiceImpl.findByStudentId(1)

        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldGetStudentsWhenCallMethodByName() {
        val expectedResult : MutableList<Student> = mutableListOf<Student>()
        expectedResult.add(Student(1, "Devin", "devin@gmail.com"))
        given(studentDao.findByName("Devin")).willReturn(expectedResult)

        val actual : MutableList<Student> = studentServiceImpl.findByStudentName("Devin")

        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldGetNewStudentWhenCallMethodByStudent() {
        val expectedResult = Student( 1, "Devin", "devin@gmail.com")
        val requestParameter = Student( name = "Devin", email = "devin@gmail.com")
        given(studentDao.save(requestParameter)).willReturn(expectedResult)

        val actual : Student = studentServiceImpl.addStudent(requestParameter)

        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldUpdatedStudentWhenCallMethodByStudent() {
        val expectedResult = Student(1, "Devin", "devin@gmail.com")
        val requestParameter = Student(1, "Eric", "eric@gmail.com")
        given(studentDao.save(requestParameter)).willReturn(expectedResult)

        val actual : Student? = studentServiceImpl.updateStudent(requestParameter)

        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldUpdatedEmailWhenCallMethodByStudent() {
        val expectedResult = Student(1, "Devin", "devin@gmail.com")
        val requestParameter = Student(1, "Devin", "test@gmail.com")
        given(studentDao.save(requestParameter)).willReturn(expectedResult)

        val actual : Student? = studentServiceImpl.updateStudentEmail(requestParameter)

        assertEquals(expectedResult.email, actual?.email)
    }

    @Test
    fun shouldDeletedStudentWhenCallMethodByStudent() {
        val expectedResult = true
        val expectedSaveResult = Student(1, "Devin", "devin@gmail.com")
        given(studentDao.findById(1)).willReturn(expectedSaveResult)

        val actual = studentServiceImpl.deleteStudent(1)

        assertEquals(expectedResult, actual)
    }

    @Test
    fun shouldGetFalseWhenDeleteFailed() {
        val expectedResult = false
        val expectedSaveResult = Student(1, "Devin", "devin@gmail.com")
        given(studentDao.findById(1)).willReturn(expectedSaveResult)
        given(studentDao.delete(expectedSaveResult)).willAnswer { throw ServiceException("Delete Failed") }

        val actual = studentServiceImpl.deleteStudent(1)

        assertEquals(expectedResult, actual)
    }
}
