package com.ironman.demo.controller.Impl

import com.ironman.demo.data.entity.Student
import com.ironman.demo.controller.StudentController
import com.ironman.demo.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/9/25
 */
@RestController
@RequestMapping("/api")
class StudentControllerImpl(@Autowired val studentService: StudentService) : StudentController {
    /**
     * 取得 Student 所有資料
     */
    override fun getStudentData(): MutableList<Student> = studentService.findAllStudent()

    /**
     * 新增 Student 資料
     */
    override fun addStudentData(student: Student): Student = studentService.addStudent(student)

    /**
     * 利用姓名查詢學生資料
     */
    override fun getStudentByName(name: String): ResponseEntity<List<Student>>
            = studentService
            .findByStudentName(name)
            .let {
                return ResponseEntity(it, HttpStatus.OK)
            }

    /**
     * 修改學生全部資料
     */
    override fun updateStudent(id: Int, student: Student): ResponseEntity<Student?>
            = studentService
            .findByStudentId(id)
            .run {
                this ?: return ResponseEntity<Student?>(null, HttpStatus.NOT_FOUND)
            }.run {
                return ResponseEntity<Student?>(studentService.updateStudent(this), HttpStatus.OK)
            }

    /**
     * 修改學生信箱（欲更新部份資料）
     */
    override fun updateStudentEmail(id: Int, student: Student): ResponseEntity<Student?>
            = studentService
            .findByStudentId(id)
            .run {
                this ?: return ResponseEntity<Student?>(null, HttpStatus.NOT_FOUND)
            }
            .run {
                Student(
                        id = this.id,
                        name = this.name,
                        email = student.email
                )
            }
            .run {
                return ResponseEntity<Student?>(studentService.updateStudentEmail(this), HttpStatus.OK)
            }

    /**
     * 刪除學生資料
     */
    override fun deleteStudent(id: Int): ResponseEntity<Any>
            = studentService
            .deleteStudent(id)
            .run {
                if (this) {
                    return ResponseEntity<Any>(null, HttpStatus.NOT_FOUND)
                }
                return ResponseEntity<Any>(null, HttpStatus.NO_CONTENT)
            }
}