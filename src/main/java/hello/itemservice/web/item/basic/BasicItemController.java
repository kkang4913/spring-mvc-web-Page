package hello.itemservice.web.item.basic;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    /**
     * 테스트용 데이터 추가
     * @PostConstruct : 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model){

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item",item);

        return "basic/item";
    }
    /**
     * 상품 등록 폼 GET
     */
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * 상품 등록 POST
     */
    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName
                            ,@RequestParam int price
                            ,@RequestParam Integer quantity
                            ,Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }

    /**
     * 상품 등록 V2
     * @ModelAttribute("item") Item item
     * model.addAttribute("item", item); 자동 추가
     */
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.save(item);
        log.info("itemName={},price={},quantity={}",item.getItemName(),item.getPrice(),item.getQuantity());
        //model.addAttribute("item", item); //자동 추가, 생략 가능
        return "basic/item";
    }

    /**
     * 상품 등록 V3
     * @ModelAttribute("item") 생략 가능
     */
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        itemRepository.save(item);
        log.info("itemName={},price={},quantity={}",item.getItemName(),item.getPrice(),item.getQuantity());

        return "basic/item";
    }
    /**
     * 상품 등록 V4
     * @ModelAttribute 자체도 생략가능하다. 대상 객체는 모델에 자동 등록된다. 나머지 사항은 기존과 동일
     */
    //@PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        log.info("itemName={},price={},quantity={}",item.getItemName(),item.getPrice(),item.getQuantity());

        return "basic/item";
    }

    /**
     * POST 방식에서 새로고침의 문제를 발견
     * 새로고침 시 마지막으로 전송한 데이터를 다시 전송
     * 중복 등록이 계속 된다.
     * redirect : 사용하면 해결된다.
     */
    //@PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
        log.info("itemName={},price={},quantity={}",item.getItemName(),item.getPrice(),item.getQuantity());

        return "redirect:/basic/items/" + item.getId();
    }
    /**
     * RedirectAttributes
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",savedItem.getId());
        redirectAttributes.addAttribute("status",true);
        log.info("itemName={},price={},quantity={}",item.getItemName(),item.getPrice(),item.getQuantity());

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item",item);

        return "basic/editForm";
    }



    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){

        log.info("itemId={},item={}",itemId,item);

        itemRepository.update(itemId,item);

        return "redirect:/basic/items/{itemId}";
    }





}