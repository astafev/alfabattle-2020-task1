package ru.astafev.alfabattle.task5.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.astafev.alfabattle.task5.model.Group;
import ru.astafev.alfabattle.task5.model.Item;
import ru.astafev.alfabattle.task5.repository.GroupRepository;
import ru.astafev.alfabattle.task5.repository.ItemRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {
    private static final String GROUPS_CSV = "classpath:groups.csv";
    private static final String ITEMS_CSV = "classpath:items.csv";

    private final GroupRepository groupRepository;
    private final ItemRepository itemRepository;

    @PostConstruct
    public void initWithCsvFiles() throws IOException {
        readAndSaveGroups();
        readAndSaveItems();
    }

    private void readAndSaveGroups() throws IOException {
        groupRepository.saveAll(readAll(Group.class, GROUPS_CSV));
    }

    private void readAndSaveItems() throws IOException {
        itemRepository.saveAll(readAll(Item.class, ITEMS_CSV));
    }

    private <T> List<T> readAll(Class<T> clazz, String uri) throws IOException {
        MappingIterator<T> objectMappingIterator =
                new CsvMapper().readerFor(clazz)
                        .with(CsvSchema.emptySchema().withHeader())
                        .readValues(ResourceUtils.getFile(uri));
        var values = objectMappingIterator.readAll();
        log.info("Read {} items of {}", values.size(), clazz.getSimpleName());
        return values;
    }
}
