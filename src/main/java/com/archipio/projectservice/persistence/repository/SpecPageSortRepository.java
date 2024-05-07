package com.archipio.projectservice.persistence.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SpecPageSortRepository<T, ID>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

  default List<T> findAll(Specification<T> spec, Pageable pageable, Sort sort) {
    if (sort == null) {
      sort = Sort.unsorted();
    }
    if (spec == null && pageable == null) {
      return findAll(sort);
    }
    if (spec == null) {
      return findAll(pageable).getContent();
    }
    if (pageable == null) {
      return findAll(spec, sort);
    }
    return findAll(spec, pageable).getContent();
  }
}
