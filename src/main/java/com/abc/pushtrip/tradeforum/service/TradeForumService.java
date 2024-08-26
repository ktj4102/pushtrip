package com.abc.pushtrip.tradeforum.service;

import com.abc.pushtrip.tradeforum.entity.TradeForum;
import com.abc.pushtrip.travelforum.entity.TravelForum;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TradeForumService {

    Page<TradeForum> selectAll(int page);

    Page<TradeForum> search(String searchCategory, String searchTerm, int page);

    void tradeForumInsert(TradeForum tradeForum);

    TradeForum tradeForumSelect(TradeForum tradeForum);

    void tradeForumUpdate(Long tradeId, TradeForum tradeForum, MultipartFile image) throws IOException;

    void tradeForumDelete(Long tradeId);

    TradeForum getTradeForumById( Long tradeId);

    void saveImage(Long tradeId, MultipartFile image) throws IOException;

    List<TradeForum> findTop6ByOrderByViewCountDesc();
}