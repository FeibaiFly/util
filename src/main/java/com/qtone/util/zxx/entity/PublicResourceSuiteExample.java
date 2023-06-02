package com.qtone.util.zxx.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicResourceSuiteExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PublicResourceSuiteExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdIsNull() {
            addCriterion("grade_level_id is null");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdIsNotNull() {
            addCriterion("grade_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdEqualTo(Integer value) {
            addCriterion("grade_level_id =", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdNotEqualTo(Integer value) {
            addCriterion("grade_level_id <>", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdGreaterThan(Integer value) {
            addCriterion("grade_level_id >", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("grade_level_id >=", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdLessThan(Integer value) {
            addCriterion("grade_level_id <", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdLessThanOrEqualTo(Integer value) {
            addCriterion("grade_level_id <=", value, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdIn(List<Integer> values) {
            addCriterion("grade_level_id in", values, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdNotIn(List<Integer> values) {
            addCriterion("grade_level_id not in", values, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdBetween(Integer value1, Integer value2) {
            addCriterion("grade_level_id between", value1, value2, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andGradeLevelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("grade_level_id not between", value1, value2, "gradeLevelId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNull() {
            addCriterion("subject_id is null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIsNotNull() {
            addCriterion("subject_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectIdEqualTo(Integer value) {
            addCriterion("subject_id =", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotEqualTo(Integer value) {
            addCriterion("subject_id <>", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThan(Integer value) {
            addCriterion("subject_id >", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("subject_id >=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThan(Integer value) {
            addCriterion("subject_id <", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("subject_id <=", value, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdIn(List<Integer> values) {
            addCriterion("subject_id in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotIn(List<Integer> values) {
            addCriterion("subject_id not in", values, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdBetween(Integer value1, Integer value2) {
            addCriterion("subject_id between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andSubjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("subject_id not between", value1, value2, "subjectId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIsNull() {
            addCriterion("material_id is null");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIsNotNull() {
            addCriterion("material_id is not null");
            return (Criteria) this;
        }

        public Criteria andMaterialIdEqualTo(Integer value) {
            addCriterion("material_id =", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotEqualTo(Integer value) {
            addCriterion("material_id <>", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdGreaterThan(Integer value) {
            addCriterion("material_id >", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("material_id >=", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdLessThan(Integer value) {
            addCriterion("material_id <", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdLessThanOrEqualTo(Integer value) {
            addCriterion("material_id <=", value, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdIn(List<Integer> values) {
            addCriterion("material_id in", values, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotIn(List<Integer> values) {
            addCriterion("material_id not in", values, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdBetween(Integer value1, Integer value2) {
            addCriterion("material_id between", value1, value2, "materialId");
            return (Criteria) this;
        }

        public Criteria andMaterialIdNotBetween(Integer value1, Integer value2) {
            addCriterion("material_id not between", value1, value2, "materialId");
            return (Criteria) this;
        }

        public Criteria andGradeIdIsNull() {
            addCriterion("grade_id is null");
            return (Criteria) this;
        }

        public Criteria andGradeIdIsNotNull() {
            addCriterion("grade_id is not null");
            return (Criteria) this;
        }

        public Criteria andGradeIdEqualTo(Integer value) {
            addCriterion("grade_id =", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdNotEqualTo(Integer value) {
            addCriterion("grade_id <>", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdGreaterThan(Integer value) {
            addCriterion("grade_id >", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("grade_id >=", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdLessThan(Integer value) {
            addCriterion("grade_id <", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdLessThanOrEqualTo(Integer value) {
            addCriterion("grade_id <=", value, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdIn(List<Integer> values) {
            addCriterion("grade_id in", values, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdNotIn(List<Integer> values) {
            addCriterion("grade_id not in", values, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdBetween(Integer value1, Integer value2) {
            addCriterion("grade_id between", value1, value2, "gradeId");
            return (Criteria) this;
        }

        public Criteria andGradeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("grade_id not between", value1, value2, "gradeId");
            return (Criteria) this;
        }

        public Criteria andChapterIdIsNull() {
            addCriterion("chapter_id is null");
            return (Criteria) this;
        }

        public Criteria andChapterIdIsNotNull() {
            addCriterion("chapter_id is not null");
            return (Criteria) this;
        }

        public Criteria andChapterIdEqualTo(Integer value) {
            addCriterion("chapter_id =", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdNotEqualTo(Integer value) {
            addCriterion("chapter_id <>", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdGreaterThan(Integer value) {
            addCriterion("chapter_id >", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("chapter_id >=", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdLessThan(Integer value) {
            addCriterion("chapter_id <", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdLessThanOrEqualTo(Integer value) {
            addCriterion("chapter_id <=", value, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdIn(List<Integer> values) {
            addCriterion("chapter_id in", values, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdNotIn(List<Integer> values) {
            addCriterion("chapter_id not in", values, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdBetween(Integer value1, Integer value2) {
            addCriterion("chapter_id between", value1, value2, "chapterId");
            return (Criteria) this;
        }

        public Criteria andChapterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("chapter_id not between", value1, value2, "chapterId");
            return (Criteria) this;
        }

        public Criteria andSuiteNameIsNull() {
            addCriterion("suite_name is null");
            return (Criteria) this;
        }

        public Criteria andSuiteNameIsNotNull() {
            addCriterion("suite_name is not null");
            return (Criteria) this;
        }

        public Criteria andSuiteNameEqualTo(String value) {
            addCriterion("suite_name =", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotEqualTo(String value) {
            addCriterion("suite_name <>", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameGreaterThan(String value) {
            addCriterion("suite_name >", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameGreaterThanOrEqualTo(String value) {
            addCriterion("suite_name >=", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLessThan(String value) {
            addCriterion("suite_name <", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLessThanOrEqualTo(String value) {
            addCriterion("suite_name <=", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameLike(String value) {
            addCriterion("suite_name like", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotLike(String value) {
            addCriterion("suite_name not like", value, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameIn(List<String> values) {
            addCriterion("suite_name in", values, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotIn(List<String> values) {
            addCriterion("suite_name not in", values, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameBetween(String value1, String value2) {
            addCriterion("suite_name between", value1, value2, "suiteName");
            return (Criteria) this;
        }

        public Criteria andSuiteNameNotBetween(String value1, String value2) {
            addCriterion("suite_name not between", value1, value2, "suiteName");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNull() {
            addCriterion("introduce is null");
            return (Criteria) this;
        }

        public Criteria andIntroduceIsNotNull() {
            addCriterion("introduce is not null");
            return (Criteria) this;
        }

        public Criteria andIntroduceEqualTo(String value) {
            addCriterion("introduce =", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotEqualTo(String value) {
            addCriterion("introduce <>", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThan(String value) {
            addCriterion("introduce >", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceGreaterThanOrEqualTo(String value) {
            addCriterion("introduce >=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThan(String value) {
            addCriterion("introduce <", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLessThanOrEqualTo(String value) {
            addCriterion("introduce <=", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceLike(String value) {
            addCriterion("introduce like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotLike(String value) {
            addCriterion("introduce not like", value, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceIn(List<String> values) {
            addCriterion("introduce in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotIn(List<String> values) {
            addCriterion("introduce not in", values, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceBetween(String value1, String value2) {
            addCriterion("introduce between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andIntroduceNotBetween(String value1, String value2) {
            addCriterion("introduce not between", value1, value2, "introduce");
            return (Criteria) this;
        }

        public Criteria andProviderIdIsNull() {
            addCriterion("provider_id is null");
            return (Criteria) this;
        }

        public Criteria andProviderIdIsNotNull() {
            addCriterion("provider_id is not null");
            return (Criteria) this;
        }

        public Criteria andProviderIdEqualTo(Integer value) {
            addCriterion("provider_id =", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdNotEqualTo(Integer value) {
            addCriterion("provider_id <>", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdGreaterThan(Integer value) {
            addCriterion("provider_id >", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("provider_id >=", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdLessThan(Integer value) {
            addCriterion("provider_id <", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdLessThanOrEqualTo(Integer value) {
            addCriterion("provider_id <=", value, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdIn(List<Integer> values) {
            addCriterion("provider_id in", values, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdNotIn(List<Integer> values) {
            addCriterion("provider_id not in", values, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdBetween(Integer value1, Integer value2) {
            addCriterion("provider_id between", value1, value2, "providerId");
            return (Criteria) this;
        }

        public Criteria andProviderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("provider_id not between", value1, value2, "providerId");
            return (Criteria) this;
        }

        public Criteria andLevelIdIsNull() {
            addCriterion("level_id is null");
            return (Criteria) this;
        }

        public Criteria andLevelIdIsNotNull() {
            addCriterion("level_id is not null");
            return (Criteria) this;
        }

        public Criteria andLevelIdEqualTo(Integer value) {
            addCriterion("level_id =", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdNotEqualTo(Integer value) {
            addCriterion("level_id <>", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdGreaterThan(Integer value) {
            addCriterion("level_id >", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("level_id >=", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdLessThan(Integer value) {
            addCriterion("level_id <", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdLessThanOrEqualTo(Integer value) {
            addCriterion("level_id <=", value, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdIn(List<Integer> values) {
            addCriterion("level_id in", values, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdNotIn(List<Integer> values) {
            addCriterion("level_id not in", values, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdBetween(Integer value1, Integer value2) {
            addCriterion("level_id between", value1, value2, "levelId");
            return (Criteria) this;
        }

        public Criteria andLevelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("level_id not between", value1, value2, "levelId");
            return (Criteria) this;
        }

        public Criteria andUsedTypeIsNull() {
            addCriterion("used_type is null");
            return (Criteria) this;
        }

        public Criteria andUsedTypeIsNotNull() {
            addCriterion("used_type is not null");
            return (Criteria) this;
        }

        public Criteria andUsedTypeEqualTo(Integer value) {
            addCriterion("used_type =", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeNotEqualTo(Integer value) {
            addCriterion("used_type <>", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeGreaterThan(Integer value) {
            addCriterion("used_type >", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("used_type >=", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeLessThan(Integer value) {
            addCriterion("used_type <", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeLessThanOrEqualTo(Integer value) {
            addCriterion("used_type <=", value, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeIn(List<Integer> values) {
            addCriterion("used_type in", values, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeNotIn(List<Integer> values) {
            addCriterion("used_type not in", values, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeBetween(Integer value1, Integer value2) {
            addCriterion("used_type between", value1, value2, "usedType");
            return (Criteria) this;
        }

        public Criteria andUsedTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("used_type not between", value1, value2, "usedType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeIsNull() {
            addCriterion("access_type is null");
            return (Criteria) this;
        }

        public Criteria andAccessTypeIsNotNull() {
            addCriterion("access_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccessTypeEqualTo(Integer value) {
            addCriterion("access_type =", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeNotEqualTo(Integer value) {
            addCriterion("access_type <>", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeGreaterThan(Integer value) {
            addCriterion("access_type >", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("access_type >=", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeLessThan(Integer value) {
            addCriterion("access_type <", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeLessThanOrEqualTo(Integer value) {
            addCriterion("access_type <=", value, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeIn(List<Integer> values) {
            addCriterion("access_type in", values, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeNotIn(List<Integer> values) {
            addCriterion("access_type not in", values, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeBetween(Integer value1, Integer value2) {
            addCriterion("access_type between", value1, value2, "accessType");
            return (Criteria) this;
        }

        public Criteria andAccessTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("access_type not between", value1, value2, "accessType");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Integer value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Integer value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Integer value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Integer value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Integer value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Integer> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Integer> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Integer value1, Integer value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Integer value1, Integer value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andResourceNumIsNull() {
            addCriterion("resource_num is null");
            return (Criteria) this;
        }

        public Criteria andResourceNumIsNotNull() {
            addCriterion("resource_num is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNumEqualTo(Integer value) {
            addCriterion("resource_num =", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumNotEqualTo(Integer value) {
            addCriterion("resource_num <>", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumGreaterThan(Integer value) {
            addCriterion("resource_num >", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("resource_num >=", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumLessThan(Integer value) {
            addCriterion("resource_num <", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumLessThanOrEqualTo(Integer value) {
            addCriterion("resource_num <=", value, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumIn(List<Integer> values) {
            addCriterion("resource_num in", values, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumNotIn(List<Integer> values) {
            addCriterion("resource_num not in", values, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumBetween(Integer value1, Integer value2) {
            addCriterion("resource_num between", value1, value2, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andResourceNumNotBetween(Integer value1, Integer value2) {
            addCriterion("resource_num not between", value1, value2, "resourceNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumIsNull() {
            addCriterion("learn_num is null");
            return (Criteria) this;
        }

        public Criteria andLearnNumIsNotNull() {
            addCriterion("learn_num is not null");
            return (Criteria) this;
        }

        public Criteria andLearnNumEqualTo(Integer value) {
            addCriterion("learn_num =", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumNotEqualTo(Integer value) {
            addCriterion("learn_num <>", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumGreaterThan(Integer value) {
            addCriterion("learn_num >", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("learn_num >=", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumLessThan(Integer value) {
            addCriterion("learn_num <", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumLessThanOrEqualTo(Integer value) {
            addCriterion("learn_num <=", value, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumIn(List<Integer> values) {
            addCriterion("learn_num in", values, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumNotIn(List<Integer> values) {
            addCriterion("learn_num not in", values, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumBetween(Integer value1, Integer value2) {
            addCriterion("learn_num between", value1, value2, "learnNum");
            return (Criteria) this;
        }

        public Criteria andLearnNumNotBetween(Integer value1, Integer value2) {
            addCriterion("learn_num not between", value1, value2, "learnNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumIsNull() {
            addCriterion("play_num is null");
            return (Criteria) this;
        }

        public Criteria andPlayNumIsNotNull() {
            addCriterion("play_num is not null");
            return (Criteria) this;
        }

        public Criteria andPlayNumEqualTo(Integer value) {
            addCriterion("play_num =", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotEqualTo(Integer value) {
            addCriterion("play_num <>", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumGreaterThan(Integer value) {
            addCriterion("play_num >", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("play_num >=", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumLessThan(Integer value) {
            addCriterion("play_num <", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumLessThanOrEqualTo(Integer value) {
            addCriterion("play_num <=", value, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumIn(List<Integer> values) {
            addCriterion("play_num in", values, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotIn(List<Integer> values) {
            addCriterion("play_num not in", values, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumBetween(Integer value1, Integer value2) {
            addCriterion("play_num between", value1, value2, "playNum");
            return (Criteria) this;
        }

        public Criteria andPlayNumNotBetween(Integer value1, Integer value2) {
            addCriterion("play_num not between", value1, value2, "playNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumIsNull() {
            addCriterion("parise_num is null");
            return (Criteria) this;
        }

        public Criteria andPariseNumIsNotNull() {
            addCriterion("parise_num is not null");
            return (Criteria) this;
        }

        public Criteria andPariseNumEqualTo(Integer value) {
            addCriterion("parise_num =", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumNotEqualTo(Integer value) {
            addCriterion("parise_num <>", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumGreaterThan(Integer value) {
            addCriterion("parise_num >", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("parise_num >=", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumLessThan(Integer value) {
            addCriterion("parise_num <", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumLessThanOrEqualTo(Integer value) {
            addCriterion("parise_num <=", value, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumIn(List<Integer> values) {
            addCriterion("parise_num in", values, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumNotIn(List<Integer> values) {
            addCriterion("parise_num not in", values, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumBetween(Integer value1, Integer value2) {
            addCriterion("parise_num between", value1, value2, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andPariseNumNotBetween(Integer value1, Integer value2) {
            addCriterion("parise_num not between", value1, value2, "pariseNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumIsNull() {
            addCriterion("collection_num is null");
            return (Criteria) this;
        }

        public Criteria andCollectionNumIsNotNull() {
            addCriterion("collection_num is not null");
            return (Criteria) this;
        }

        public Criteria andCollectionNumEqualTo(Integer value) {
            addCriterion("collection_num =", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumNotEqualTo(Integer value) {
            addCriterion("collection_num <>", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumGreaterThan(Integer value) {
            addCriterion("collection_num >", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("collection_num >=", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumLessThan(Integer value) {
            addCriterion("collection_num <", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumLessThanOrEqualTo(Integer value) {
            addCriterion("collection_num <=", value, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumIn(List<Integer> values) {
            addCriterion("collection_num in", values, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumNotIn(List<Integer> values) {
            addCriterion("collection_num not in", values, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumBetween(Integer value1, Integer value2) {
            addCriterion("collection_num between", value1, value2, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andCollectionNumNotBetween(Integer value1, Integer value2) {
            addCriterion("collection_num not between", value1, value2, "collectionNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumIsNull() {
            addCriterion("evaluate_num is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumIsNotNull() {
            addCriterion("evaluate_num is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumEqualTo(Integer value) {
            addCriterion("evaluate_num =", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumNotEqualTo(Integer value) {
            addCriterion("evaluate_num <>", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumGreaterThan(Integer value) {
            addCriterion("evaluate_num >", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("evaluate_num >=", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumLessThan(Integer value) {
            addCriterion("evaluate_num <", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumLessThanOrEqualTo(Integer value) {
            addCriterion("evaluate_num <=", value, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumIn(List<Integer> values) {
            addCriterion("evaluate_num in", values, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumNotIn(List<Integer> values) {
            addCriterion("evaluate_num not in", values, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumBetween(Integer value1, Integer value2) {
            addCriterion("evaluate_num between", value1, value2, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateNumNotBetween(Integer value1, Integer value2) {
            addCriterion("evaluate_num not between", value1, value2, "evaluateNum");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIsNull() {
            addCriterion("evaluate_score is null");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIsNotNull() {
            addCriterion("evaluate_score is not null");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreEqualTo(Float value) {
            addCriterion("evaluate_score =", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotEqualTo(Float value) {
            addCriterion("evaluate_score <>", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreGreaterThan(Float value) {
            addCriterion("evaluate_score >", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreGreaterThanOrEqualTo(Float value) {
            addCriterion("evaluate_score >=", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreLessThan(Float value) {
            addCriterion("evaluate_score <", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreLessThanOrEqualTo(Float value) {
            addCriterion("evaluate_score <=", value, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreIn(List<Float> values) {
            addCriterion("evaluate_score in", values, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotIn(List<Float> values) {
            addCriterion("evaluate_score not in", values, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreBetween(Float value1, Float value2) {
            addCriterion("evaluate_score between", value1, value2, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andEvaluateScoreNotBetween(Float value1, Float value2) {
            addCriterion("evaluate_score not between", value1, value2, "evaluateScore");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNull() {
            addCriterion("delete_time is null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNotNull() {
            addCriterion("delete_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeEqualTo(Date value) {
            addCriterion("delete_time =", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotEqualTo(Date value) {
            addCriterion("delete_time <>", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThan(Date value) {
            addCriterion("delete_time >", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delete_time >=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThan(Date value) {
            addCriterion("delete_time <", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("delete_time <=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIn(List<Date> values) {
            addCriterion("delete_time in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotIn(List<Date> values) {
            addCriterion("delete_time not in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeBetween(Date value1, Date value2) {
            addCriterion("delete_time between", value1, value2, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("delete_time not between", value1, value2, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}