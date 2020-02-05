/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.clinic.services;

import java.util.List;

/**
 *
 */
public interface EntityService<E> {
    E create(E e);
    void delete(E e);
    E update(E e);
    E get(Long id);
    List<E> getAll();
}
