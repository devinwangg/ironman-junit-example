package com.ironman.demo.service.Impl

import com.ironman.demo.data.dao.StudentDao
import com.ironman.demo.data.entity.Student
import com.ironman.demo.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/9/29
 */
@Service
class StudentServiceImpl(@Autowired val studentDao: StudentDao) : StudentService {
    override fun findAllStudent(): MutableList<Student> = studentDao.findAll()

    override fun addStudent(student: Student): Student =
            Student(
                    name = student.name.trim(),
                    email = student.email.trim()
            ).run {
                return studentDao.save(this)
            }

    override fun findByStudentId(id: Int): Student? = studentDao.findById(id)

    override fun findByStudentName(name: String): MutableList<Student> = studentDao.findByName(name)

    override fun updateStudent(student: Student): Student =
            Student(
                    id = student.id,
                    name = student.name.trim(),
                    email = student.email.trim()
            ).run {
                return studentDao.save(this)
            }

    override fun updateStudentEmail(student: Student): Student =
            Student(
                    id = student.id,
                    name = student.name,
                    email = student.email.trim()
            ).run {
                return studentDao.save(this)
            }

    override fun deleteStudent(id: Int): Boolean =
            studentDao.findById(id)
                    .run {
                        this ?: false
                    }.run {
                        return try {
                            studentDao.delete(this as Student)
                            true
                        } catch (exception: Exception) {
                            false
                        }
                    }
}