/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.model.domain.company.entity;
// Generated Jan 7, 2019 10:43:39 AM by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import com.randomlogic.rlpay.model.domain.interfaces.IAPIUser;

/**
 * User generated by hbm2java
 */
@Entity
@Table
(
    name = "Users",
    catalog = "rlapi",
    uniqueConstraints = @UniqueConstraint (columnNames = "idUser")
)
public class User implements IAPIUser, Serializable
{
     private UserId id = new UserId();
     private String password;
     private String email;
     private byte valid;
     private String token;
     private int encLevel;
     private String encType;
     private Date creation;
////     private Set authorizations = new HashSet(0);

    public User()
    {
    }

    public User (Integer id)
    {
        this.id.setIdUser (id);
    }

    public User (UserId id, String password, byte valid, Date creation)
    {
        this.id = id;
        this.password = password;
        this.valid = valid;
        this.creation = creation;
    }

    public User (UserId id,
                 String password,
                 String email,
                 byte valid,
                 String token,
                 Date creation //,
////                 Set authorizations
                )
    {
       this.id = id;
       this.password = password;
       this.email = email;
       this.valid = valid;
       this.token = token;
       this.creation = creation;
////       this.authorizations = authorizations;
    }

    public User (UserId id,
                 String password,
                 String email,
                 byte valid,
                 String token,
                 int encLevel,
                 String encType,
                 Date creation //,
////                 Set authorizations
                )
    {
       this.id = id;
       this.password = password;
       this.email = email;
       this.valid = valid;
       this.token = token;
       this.encLevel = encLevel;
       this.encType = encType;
       this.creation = creation;
////       this.authorizations = authorizations;
    }

    @Column (name = "encLevel", nullable = false)
    @Override
    public int getEncLevel()
    {
        return this.encLevel;
    }

    @Override
    public void setEncLevel (int encLevel)
    {
        this.encLevel = encLevel;
    }

    @Column (name = "encType", nullable = false, length = 10)
    @Override
    public String getEncType()
    {
        return this.encType;
    }

    @Override
    public void setEncType (String encType)
    {
        this.encType = encType;
    }

    @EmbeddedId
    @AttributeOverrides
    (
        {
            @AttributeOverride (name = "idUser", column = @Column (name = "idUser", unique = true, nullable = false)),
            @AttributeOverride (name = "uid", column = @Column (name = "uid", nullable = false, length = 45)),
            @AttributeOverride (name = "deviceid", column = @Column (name = "deviceid", nullable = false, length = 64))
        }
    )
    @Override
    public UserId getId()
    {
        return this.id;
    }

    @Override
    public void setId (UserId id)
    {
        this.id = id;
    }

    @Column (name = "password", nullable = false, length = 4096)
    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public void setPassword (String password)
    {
        this.password = password;
    }


    @Column (name = "email")
    @Override
    public String getEmail()
    {
        return this.email;
    }

    @Override
    public void setEmail (String email)
    {
        this.email = email;
    }


    @Column (name = "valid", nullable = false)
    @Override
    public byte getValid()
    {
        return this.valid;
    }

    @Override
    public void setValid (byte valid)
    {
        this.valid = valid;
    }


    @Column (name = "token", length = 64)
    @Override
    public String getToken()
    {
        return this.token;
    }

    @Override
    public void setToken (String token)
    {
        this.token = token;
    }

    @Temporal (TemporalType.TIMESTAMP)
    @Column (name = "creation", nullable = false, length = 19)
    @Override
    public Date getCreation()
    {
        return this.creation;
    }

    @Override
    public void setCreation (Date creation)
    {
        this.creation = creation;
    }

////    @ManyToMany(fetch=FetchType.EAGER)
////    @JoinTable(name="user_has_authorization", catalog="dccapi", joinColumns = {
////        @JoinColumn(name="User_idUser", nullable=false, updatable=false),
////        @JoinColumn(name="User_uid", nullable=false, updatable=false),
////        @JoinColumn(name="User_deviceid", nullable=false, updatable=false) }, inverseJoinColumns = {
////        @JoinColumn(name="Authorization_idAuthorization", nullable=false, updatable=false) })
////    @Override
////    public Set getAuthorizations()
////    {
////        return this.authorizations;
////    }

////    @Override
////    public void setAuthorizations (Set authorizations)
////    {
////        this.authorizations = authorizations;
////    }
}