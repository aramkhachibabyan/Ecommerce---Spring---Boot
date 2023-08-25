package com.smartCode.ecommerce.spec.user;

import com.smartCode.ecommerce.model.entity.user.UserEntity;
import com.smartCode.ecommerce.util.constants.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Component
public class UserSpecification {

    public Specification<UserEntity> search(com.smartCode.ecommerce.model.dto.user.FilterSearchUser.Search userSearch) {
        return Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (nonNull(userSearch.getText())) {
                Predicate nameLike = criteriaBuilder.like(root.get(Root.NAME), "%" + userSearch.getText() + "%");
                predicates.add(nameLike);

                Predicate lastNameLike = criteriaBuilder.like(root.get(Root.LASTNAME), "%" + userSearch.getText() + "%");
                predicates.add(lastNameLike);

                Predicate emailLike = criteriaBuilder.like(root.get(Root.EMAIL), "%" + userSearch.getText() + "%");
                predicates.add(emailLike);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        });
    }

    public Specification<UserEntity> filter(com.smartCode.ecommerce.model.dto.user.FilterSearchUser.Filter userFilter) {
        return Specification.where((root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (nonNull(userFilter.getIsVerified())) {
                Predicate isVerified = criteriaBuilder.equal(root.get(Root.IS_VERIFIED), userFilter.getIsVerified());
                predicates.add(isVerified);
            }
            if (nonNull(userFilter.getGender())) {
                Predicate gender = criteriaBuilder.equal(root.get(Root.GENDER), userFilter.getGender());
                predicates.add(gender);
            }
            if (nonNull(userFilter.getStartAge())) {
                Predicate startAge = criteriaBuilder.greaterThan(root.get(Root.AGE), userFilter.getStartAge());
                predicates.add(startAge);
            }
            if (nonNull(userFilter.getEndAge())) {
                Predicate endAge = criteriaBuilder.lessThan(root.get(Root.AGE), userFilter.getEndAge());
                predicates.add(endAge);
            }
            /*if (nonNull(userFilter.getProductName())) {
                Join<UserEntity, ProductEntity> products = root.join("productEntities");
                Predicate productName = criteriaBuilder.equal(products.get("name"), userFilter.getProductName());
                predicates.add(productName);
            }*/
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
