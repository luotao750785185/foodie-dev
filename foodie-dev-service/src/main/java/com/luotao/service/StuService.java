package com.luotao.service;

/**
 * @author : luo
 * @date : 2020/3/14 0:08
 */
public interface StuService {
    void saveParent();

    void saveChildren_REQUIRED();

    void saveChildren_SUPPORTS();

    void saveChildren_MANDATORY();

    void saveChildren_REQUIRES_NEW();

    void saveChildren_NOT_SUPPORTED();

    void saveChildren_NEVER();

    void saveChildren_NESTED();
}
