package com.abc.pushtrip.tradeforum.service;

import com.abc.pushtrip.tradeforum.entity.TradeForum;
import com.abc.pushtrip.tradeforum.repository.TradeForumRepository;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeForumServiceImpl  implements TradeForumService {

    @Autowired
    private TradeForumRepository tradeForumRepository;

    //selectAll로 엔터티 모두 가져오기
    @Override
    public Page<TradeForum> selectAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return tradeForumRepository.findAll(pageable);
    }

    @Override
    public Page<TradeForum> search(String searchCategory, String searchTerm, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("insertDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));

        return tradeForumRepository.search(searchCategory, searchTerm, pageable);
    }

    @Override
    public void tradeForumInsert(TradeForum tradeForum){

        tradeForumRepository.save(tradeForum);
    }

    @Override
    public TradeForum tradeForumSelect(TradeForum tradeForum){
        TradeForum findTradeForum = tradeForumRepository.findById(tradeForum.getTradeId()).get();
        findTradeForum.setViewCount(findTradeForum.getViewCount()+1);
        tradeForumRepository.save(findTradeForum);

        return findTradeForum;
    }

    @Override
    public void tradeForumUpdate(Long tradeId, TradeForum tradeForum, MultipartFile image) throws IOException {
        TradeForum findTradeForum = tradeForumRepository.findById(tradeId)
                .orElseThrow(() -> new EntityNotFoundException("TradeForum not found"));
        findTradeForum.setTitle(tradeForum.getTitle());
        findTradeForum.setPrice(tradeForum.getPrice());
        findTradeForum.setContent(tradeForum.getContent());
        if (!image.isEmpty()) {
            findTradeForum.setPictureFile(image.getBytes());
        }
        findTradeForum.setUpdateDate(new Date());
        tradeForumRepository.save(findTradeForum);
    }

    @Override
    public void tradeForumDelete(Long tradeId) {
        System.out.println("TradeForumService Delete >>> true");

        // 엔티티가 데이터베이스에 존재하는지 확인
        Optional<TradeForum> existingTradeForum = tradeForumRepository.findById(tradeId);

        if (existingTradeForum.isPresent()) {
            // 엔티티가 존재하면 deleteyn 값을 "Y"로 설정하고 저장
            TradeForum forum = existingTradeForum.get();
            forum.setDeleteYn("Y");
            tradeForumRepository.save(forum);
        } else {
            // 엔티티가 존재하지 않는 경우 처리
            System.out.println("ID not found : " + tradeId);
        }
    }

    @Override
    public void saveImage(Long tradeId, MultipartFile image) throws IOException {
        Optional<TradeForum> optionalTradeForum = tradeForumRepository.findById(tradeId);
        if (optionalTradeForum.isPresent()) {
            TradeForum tradeForum = optionalTradeForum.get();
            tradeForum.setPictureFile(image.getBytes());
            tradeForumRepository.save(tradeForum);
        } else {
            throw new RuntimeException("TradeForum not found");
        }
    }

    public TradeForum getTradeForumById(Long tradeId) {
        return tradeForumRepository.findById(tradeId).orElse(null);
    }

    public List<TradeForum> findTop6ByOrderByViewCountDesc() {
        return tradeForumRepository.findTop6ByOrderByViewCountDesc();
    }
}

