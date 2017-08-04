/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.krismorte.simplerepository.audit;

/**
 *
 * @author c007329
 */
public interface PersistenceRule<T extends AuditableEnitity> {
    
    public void createTime(T entity);
    public void updateTime(T entity);

    
}
