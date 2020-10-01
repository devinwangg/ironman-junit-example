package com.ironman.demo.service

import com.ironman.demo.data.entity.Student

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/9/29
 */
interface StudentService {

    /**
     * 查詢所有學生資料
     */
    fun findAllStudent(): MutableList<Student>

    /**
     * 新增學生資料
     */
    fun addStudent(student: Student): Student

    /**
     * 查詢符合姓名條件的學生資料
     */
    fun findByStudentId(id: Int): Student?

    /**
     * 查詢符合姓名條件的學生資料
     */
    fun findByStudentName(name: String): MutableList<Student>

    /**
     * 更新學生整個資料
     */
    fun updateStudent(student: Student): Student

    /**
     * 更新學生信箱資料
     */
    fun updateStudentEmail(student: Student): Student

    /**
     * 刪除學生資料
     */
    fun deleteStudent(id: Int): Boolean
}