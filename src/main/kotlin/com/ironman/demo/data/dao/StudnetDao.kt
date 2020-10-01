package com.ironman.demo.data.dao

import com.ironman.demo.data.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/**
 *
 * @author wei-xiang
 * @version 1.0
 * @date 2020/9/26
 */
interface StudentDao:  JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    /**
     * 查詢符合姓名條件的學生資料
     */
    fun findByName(name: String): MutableList<Student>

    /**
     * 查詢符合 Id 條件的學生資料
     */
    fun findById(id: Int): Student?
}