package com.smartCode.ecommerce.spec.product;

import com.smartCode.ecommerce.model.dto.product.ProductFilterModel;
import com.smartCode.ecommerce.model.dto.product.ProductFilterSearchRequest;
import com.smartCode.ecommerce.model.dto.product.ProductSearchModel;
import com.smartCode.ecommerce.model.entity.product.ProductEntity;
import com.smartCode.ecommerce.util.constants.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class ProductSpecification {
   public Specification<ProductEntity> searchAndFilter(ProductFilterSearchRequest productFilterSearchRequest) {

        return Specification.where((root, query, criteriaBuilder) -> {

            var predicates = new ArrayList<Predicate>();

            predicates.add(filter(productFilterSearchRequest.getFilterModel()).toPredicate(root, query, criteriaBuilder));
            predicates.add(searchByField(productFilterSearchRequest.getSearchModel()).toPredicate(root, query, criteriaBuilder));
            predicates.add(fullTextSearch(productFilterSearchRequest.getSearchModel()).toPredicate(root, query, criteriaBuilder));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    private Specification<ProductEntity> searchByField(ProductSearchModel search) {
        return Specification.where((root, criteriaQuery, criteriaBuilder) -> {

            if (Objects.nonNull(search)) {

                Predicate searchPredicate;
                var predicates = new ArrayList<Predicate>();

                if (nonNull(search.getText())) {
                    searchPredicate = criteriaBuilder
                            .like(root.get(Root.NAME), search.getText());
                    predicates.add(searchPredicate);
                }
                if (nonNull(search.getDescription())) {
                    searchPredicate = criteriaBuilder
                            .like(root.get(Root.DESCRIPTION), search.getDescription());
                    predicates.add(searchPredicate);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            return criteriaBuilder.conjunction();
        });
    }

    private Specification<ProductEntity> fullTextSearch(ProductSearchModel search) {
        return Specification.where((root, criteriaQuery, criteriaBuilder) -> {

            if (Objects.nonNull(search)) {

                Predicate searchPredicate;
                var predicates = new ArrayList<Predicate>();

                searchPredicate = criteriaBuilder
                        .like(root.get(Root.NAME), search.getText());
                predicates.add(searchPredicate);
                searchPredicate = criteriaBuilder
                        .like(root.get(Root.DESCRIPTION), search.getDescription());
                predicates.add(searchPredicate);

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            return criteriaBuilder.conjunction();
        });
    }

    private Specification<ProductEntity> filter(ProductFilterModel filter) {
        return Specification.where((root, criteriaQuery, criteriaBuilder) -> {

            if (Objects.nonNull(filter)) {

                Predicate searchPredicate;
                var predicates = new ArrayList<Predicate>();

                if (nonNull(filter.getStartDate())) {
                    searchPredicate = criteriaBuilder
                            .greaterThanOrEqualTo(root.get(Root.PRODUCTION_DATE), filter.getStartDate());
                    predicates.add(searchPredicate);
                }
                if (nonNull(filter.getEndDate())) {
                    searchPredicate = criteriaBuilder
                            .lessThanOrEqualTo(root.get(Root.PRODUCTION_DATE), filter.getEndDate());
                    predicates.add(searchPredicate);
                }


                if (nonNull(filter.getStartPrice())) {
                    searchPredicate = criteriaBuilder
                            .greaterThanOrEqualTo(root.get(Root.PRICE), filter.getStartPrice());
                    predicates.add(searchPredicate);
                }
                if (nonNull(filter.getEndPrice())) {
                    searchPredicate = criteriaBuilder
                            .lessThanOrEqualTo(root.get(Root.PRICE), filter.getEndPrice());
                    predicates.add(searchPredicate);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
            return criteriaBuilder.conjunction();
        });
    }
}
