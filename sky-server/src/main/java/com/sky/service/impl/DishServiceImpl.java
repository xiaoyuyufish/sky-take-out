package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.FlavorMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;

    @Resource
    private FlavorMapper flavorMapper;

    @Resource
    private SetMealDishMapper setMealDishMapper;

    @Resource
    private FlavorMapper dishFlavorMapper;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     * */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        log.info("新增菜品{}", dishDTO);
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);
        Long dishId = dish.getId();
        log.info("dishId={}", dishId);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        //给每个flavor的dishId赋值
        flavors.forEach(
                flavor->{flavor.setDishId(dishId);}
        );

        flavorMapper.insertBatch(flavors);


    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return page
     * */
    @Override
    public PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO) {

        //开启分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page page = dishMapper.dishPageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {

        //检查是否起售
        for(Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //检查是否包含在套餐中
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishId(ids);
        log.info("ids = {}", setMealIds);
        if(setMealIds != null && setMealIds.size() > 0) {
            throw  new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        }

        for(Long id : ids) {
            dishMapper.deleteById(id);
            flavorMapper.deleteByDishId(id);
        }


    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {

        Dish dish = dishMapper.getById(id);
        List<DishFlavor> flavors = flavorMapper.getByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.update(dish);

        flavorMapper.deleteByDishId(dish.getId());

        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(flavor->flavor.setDishId(dish.getId()));

        flavorMapper.insertBatch(flavors);
    }

    @Override
    public List<DishVO> getByCategoryId(Long categoryId) {
        List<Dish> dishes = dishMapper.getByCategoryId(categoryId);
        List<DishVO> dishVOS = new ArrayList<>();
        for(Dish dish : dishes) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);
            dishVO.setFlavors(flavorMapper.getByDishId(dish.getId()));
            dishVOS.add(dishVO);
        }
        return dishVOS;
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }


}
