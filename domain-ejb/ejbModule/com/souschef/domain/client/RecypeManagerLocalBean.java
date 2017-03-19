package com.souschef.domain.client;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless(name = "RecypeManagerLocalBean", mappedName = "RecypeManagerLocalBean")
@Local(RecypeManagerLocal.class)
public class RecypeManagerLocalBean  extends RecypeManagerImpl{

}
