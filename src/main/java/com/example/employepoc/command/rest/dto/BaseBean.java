package com.example.employepoc.command.rest.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;

public abstract class BaseBean implements Serializable, Cloneable {
    private static final long  serialVersionUID   = 1L;
    @Field("baseBeanData")

    private transient Object   data;

    private boolean              deleted           = false;
    private boolean              readonly        = false;
    private long               version;
}