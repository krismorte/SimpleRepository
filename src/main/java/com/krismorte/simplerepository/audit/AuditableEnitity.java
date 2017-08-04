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
public interface AuditableEnitity {

    public LocalDateTime getCreateTime();

    public void setCreateTime(LocalDateTime createTime);

    public LocalDateTime getUpdateTime();

    public void setUpdateTime(LocalDateTime updateTime);

    public boolean getActive();

    public void setActive(boolean active);

}
