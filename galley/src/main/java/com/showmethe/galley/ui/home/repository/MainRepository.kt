package com.showmethe.galley.ui.home.repository

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.showmethe.galley.database.DataSourceBuilder
import com.showmethe.galley.database.dto.CartDto
import com.showmethe.galley.database.dto.GoodsListDto
import com.showmethe.galley.database.dto.PhotoWallDto
import com.showmethe.galley.database.dto.UserDto
import com.showmethe.galley.entity.CartListBean
import showmethe.github.core.base.BaseRepository
import java.sql.RowId

/**
 * Author: showMeThe
 * Update Time: 2019/10/18 16:19
 * Package Name:com.showmethe.galley.ui.home.repository
 */
class MainRepository : BaseRepository() {

    private  val photoDao = DataSourceBuilder.getPhotoWall()
    private  val userDao = DataSourceBuilder.getUser()
    private  val goodsDao = DataSourceBuilder.getGoods()


    fun getHomePhoto(bean : MutableLiveData<List<PhotoWallDto>>){
        photoDao.getPhotoBean().observe(owner!!, Observer {
            it?.apply {
                bean.postValue(this)
            }
        })
    }


    fun setPhotoLike(id: Int,like:Boolean){
        photoDao.setIdLike(id,like)
    }


    fun getUserByName(userName : String,bean : MutableLiveData<UserDto>){
        showLoading()
        userDao.getUserByName(userName).observe(owner!!, Observer {
            dismissLoading()
            if(it == null){
                showToast("请在WanAndroid端登录一次")
            }else{
                bean.value = it
            }
        })
    }


    fun findByPage(page:Int,bean : MutableLiveData<List<GoodsListDto>>){
        goodsDao.findByPage(page,10).observe(owner!!, Observer {
            it?.apply {
                bean.value  = this
            }
        })
    }


    fun addCart(dto:CartDto){
        goodsDao.addCart(dto)
        showToast("Add Successfully!")
    }

    fun getCartList(list : MutableLiveData<List<CartListBean>>){
       goodsDao.getCartList().observe(owner!!, Observer {
           it?.apply {
               list.value = this
           }
       })
    }

}