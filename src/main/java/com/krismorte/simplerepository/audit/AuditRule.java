/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krismorte.simplerepository.audit;

import java.time.LocalDateTime;

/**
 *
 * @author c007329
 */
public class AuditRule implements PersistenceRule<AuditableEnitity> {


    @Override
    public void createTime(AuditableEnitity entity) {
        entity.setCreateTime(LocalDateTime.now());
        entity.setActive(true);
    }

    @Override
    public void updateTime(AuditableEnitity entity) {
        entity.setUpdateTime(LocalDateTime.now());
    }

}
