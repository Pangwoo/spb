package kr.co.itwillbs.thymeleaf.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public void registItem(ItemDTO itemDTO) {
		Item newItem = itemDTO.toEntity();
		log.info(newItem);
		// TODO Auto-generated method stub
		itemRepository.save(newItem);
	}

	public List<ItemDTO> getItems() {
		List<Item> items = itemRepository.findAll();
		
		return toItemDTOList(items);
	}
	
	private List<ItemDTO> toItemDTOList(List<Item> items){
		return items.stream()
				.map(ItemDTO :: fromEntity)
				.collect(Collectors.toList());
	}
	
	private List<Item> toItemList(List<ItemDTO> items){
		return items.stream()
				.map(ItemDTO :: toEntity)
				.collect(Collectors.toList());
	}

}
