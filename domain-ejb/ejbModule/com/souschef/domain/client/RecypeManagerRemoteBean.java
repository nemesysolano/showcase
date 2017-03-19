package com.souschef.domain.client;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless(name = "RecypeManagerRemoteBean", mappedName = "RecypeManagerRemoteBean")
@Remote(RecypeManagerRemote.class)
public class RecypeManagerRemoteBean  extends RecypeManagerImpl{

}
