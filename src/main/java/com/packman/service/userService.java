package com.packman.service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.text.html.parser.Entity;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.packman.Entity.UserEntity;
import com.packman.repository.userRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class userService implements userRepository{

    private final userRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public userService(userRepository userRepository){
        this.userRepository = userRepository;
    }

    //이부분은 변경한 부분을 강제로 반영하도록 구현된 부분분
    @Override
    public void flush() {
        // TODO Auto-generated method stub
        entityManager.flush();
    }
    
    @Override
    public <S extends UserEntity> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        //값이 있을 수도 없을수도 있다는 것을 표현 null을 허용하는 것이 아니다다
        Optional<UserEntity> existingUser = userRepository.findByEmail(entity.getEmail());
        if(existingUser.isEmpty()){
            entityManager.persist(entity);
        }
        else{
            entity=entityManager.merge(entity);
        }
        return entity;
    }

    @Override
    public <S extends UserEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        List <S> savedUser = new ArrayList<>();
        for(S entity : entities){
            Optional<UserEntity> entitys = userRepository.findByEmail(entity.getEmail());
            if(entitys.isPresent()){
                log.info( "해당 email이 존재합니다 : {}",entity.getEmail());
                continue;
            }
            entityManager.persist(entitys);
            savedUser.add(entity);
        }
        entityManager.flush();
        return savedUser;
    }

    //여러개의 데이터를 삭제하기 위해서 존재함
    @Override
    @Transactional
    public void deleteAllInBatch(Iterable<UserEntity> entities) {
        // TODO Auto-generated method stub

        //요소가 하나라도 있는지 확인 하는 부분
        //Iterable<UserEntity> 객체를 사용해서 다음 has값이 있는지 확인
        if(!entities.iterator().hasNext()){
            log.info("삭제할것이 없습니다.");
            return;
        }
        //존재하는 id만 반환환

        log.info("삭제 완료 : {}",entities);
        userRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> ids) {
        // TODO Auto-generated method stub
        if(!ids.iterator().hasNext()){
            log.info("삭제할것이 없습니다.");
            return;
        }
        List<Long> exListid = userRepository.findAllById(ids)
                                            .stream()
                                            .map(UserEntity::getId)
                                            .collect(Collectors.toList());
        log.info("삭제 완료 : {}",exListid);
        userRepository.deleteAllByIdInBatch(exListid);
    }

    @Override
    //모든 데이터 삭제 그러나 만들지 않을 예정정
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
    }

    @Override
    public UserEntity getOne(Long id) {
        // TODO Auto-generated method stub
        return findById(id)
            .orElseThrow(()->new EntityNotFoundException("해당 ID를 가진 유저를 찾을 수 없습니다: " + id));
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("해당 ID를 가진 유저를 찾을 수 없습니다: " + id));
    }
    
  
    //getReferenceById(Long id)는 JPA에서 제공하는 메서드로, 특정 ID에 대한 프록시 객체(Proxy)를 반환하는 역할을 합니다.

    
    @Override
    public UserEntity getReferenceById(Long id) {
        // TODO Auto-generated method stub
        
        return userRepository.getReferenceById(id);    
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return userRepository.findAll(example);
    }

    @Override
    public <S extends UserEntity> List<S> findAll(Example<S> example, Sort sort) {
        return userRepository.findAll(example, sort);
    }

    @Override
    public <S extends UserEntity> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return userRepository.saveAll(entities);
    }

    @Override
    public List<UserEntity> findAll() {
        // TODO Auto-generated method stub
        return userRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllById(Iterable<Long> ids) {
        // TODO Auto-generated method stub
       return userRepository.findAllById(ids);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        return userRepository.existsById(id);
    }

    @Override
    public long count() {
        return userRepository.count();
    }


    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        Optional<UserEntity> isNow = userRepository.findById(id);
        if(isNow.isPresent())userRepository.deleteById(id);
    }

    @Override
    public void delete(UserEntity entity) {
        Optional<UserEntity> isNow = userRepository.findById(entity.getId());
        if(isNow.isPresent())userRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> ids) {
        List <Long> deleteList  = new ArrayList<>();
        if(!ids.iterator().hasNext()){
            log.info("삭제할 ID가 없습니다.");
            return;
        }else{
            for(long id : ids){
                Optional<UserEntity> isNow = userRepository.findById(id);
                deleteList.add(id);
            }
        }
        userRepository.deleteAllById(ids);

    }   

    @Override
    public void deleteAll(Iterable<? extends UserEntity> entities) {
        List <UserEntity> deleteList  = new ArrayList<>();
        if(!entities.iterator().hasNext()){
            log.info("삭제할 Entity가 없습니다.");
            return;
        }else{
            for(UserEntity user : entities){
                Optional<UserEntity> isNow = userRepository.findByEmail(user.getEmail());
                deleteList.add(user);
            }
        }
        userRepository.deleteAll(deleteList);
    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return userRepository.findAll(sort);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return userRepository.findAll(pageable);
    }

    @Override
    public <S extends UserEntity> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    //이렇게 상속을 받으면 위험함 위임을 받는 형식으로 의존성 주입하는 것이 더 좋음음
    @Override
    public <S extends UserEntity> Page<S> findAll(Example<S> example, Pageable pageable){
        return userRepository.findAll(example, pageable);
    }


    //갯수 새기기
    @Override
    public <S extends UserEntity> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return userRepository.count();
    }

    @Override
    public <S extends UserEntity> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exists'");
    }

    //쿼리 검색
    @Override
    public <S extends UserEntity, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return userRepository.findBy(example, queryFunction);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        // TODO Auto-generated method stub
        Optional<UserEntity> user = userRepository.findByEmail(email);
        log.info(user.toString());
        return user;
    }

    @Override
    public UserEntity save(UserEntity user) {
        // TODO Auto-generated method stub
        log.info(user.toString());
        return userRepository.save(user);
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
    }
}
