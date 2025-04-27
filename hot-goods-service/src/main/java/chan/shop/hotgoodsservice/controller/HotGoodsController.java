package chan.shop.hotgoodsservice.controller;


import chan.shop.hotgoodsservice.response.HotGoodsResponse;
import chan.shop.hotgoodsservice.service.HotGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotGoodsController {
    private final HotGoodsService hotGoodsService;

    @GetMapping("hot-goods/goods/date/{dateStr}")
    public List<HotGoodsResponse> readAll(@PathVariable("dateStr") String dateStr) {
        return hotGoodsService.readAll(dateStr);
    }
}
