package com.huchcode.train.android.sample.chap2.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huchcode.train.android.sample.chap2.domain.BusinessCard;


/**
 * The provider which is replacting the local database
 * 로컬 데이터 베이스를 대체하는 프로바이더
 * 
 * @author ni
 * 
 */
public class BusinessCardProvider {
    private static BusinessCardProvider businessCardProvider;

    public static BusinessCardProvider getInstance() {
        if (businessCardProvider == null) {
            businessCardProvider = new BusinessCardProvider();
        }
        return businessCardProvider;
    }

    // ==========================
    // instance area

    private Map<Long, BusinessCard> db;

    private long id;

    private BusinessCardProvider() {
        this.db = new HashMap<Long, BusinessCard>();

        loadInitialData();
    }

    private long nextId() {
        return this.id++;
    }

    public void registerBusinessCard(BusinessCard businessCard) {
        businessCard.setId(this.nextId());
        this.db.put(businessCard.getId(), businessCard);
    }

    public void modifyBusinessCard(BusinessCard businessCard) {
        BusinessCard found = this.db.get(businessCard.getId());
        found.setName(businessCard.getName());
        found.setCompany(businessCard.getCompany());
        found.setEmail(businessCard.getEmail());
        found.setImageUrl(businessCard.getImageUrl());
        found.setPhoneNo(businessCard.getPhoneNo());
    }

    public void removeBusinessCard(long id) {
        this.db.remove(id);
    }

    public BusinessCard getBusinessCard(long id) {
        return this.db.get(id);
    }

    public List<BusinessCard> findAllBusinessCard() {
        return Arrays.asList(this.db.values().toArray(new BusinessCard[] {}));
    }

    private void loadInitialData() {
        BusinessCard card = null;

        card = new BusinessCard();
        card.setId(this.nextId());
        card.setName("Jerick");
        card.setCompany("Hucocode corporation");
        card.setEmail("jerick@huchcode.com");
        card.setPhoneNo("+639101112222");
        this.db.put(card.getId(), card);

        card = new BusinessCard();
        card.setId(this.nextId());
        card.setName("Mark");
        card.setCompany("Hucocode corporation");
        card.setEmail("Mark@huchcode.com");
        card.setPhoneNo("+639201112222");
        this.db.put(card.getId(), card);

        card = new BusinessCard();
        card.setId(this.nextId());
        card.setName("Ronald");
        card.setCompany("Hucocode corporation");
        card.setEmail("Ronald@huchcode.com");
        card.setPhoneNo("+639301112222");
        this.db.put(card.getId(), card);
    }

}
