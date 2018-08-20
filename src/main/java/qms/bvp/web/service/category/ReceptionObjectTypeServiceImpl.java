package qms.bvp.web.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionObjectType;
import qms.bvp.web.repository.category.ReceptionObjectTypeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Service
public class ReceptionObjectTypeServiceImpl implements ReceptionObjectTypeService{
    @Autowired
    ReceptionObjectTypeRepository typeRepository;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        return typeRepository.page(page);
    }

    @Override
    public Optional<List<ReceptionObjectType>> listAll() {
        return Optional.of(typeRepository.findAll());
    }

    @Override
    public Optional<List<ReceptionObjectType>> listAllActive() {
        return typeRepository.listAllActive();
    }

    @Override
    public Optional<List<ReceptionObjectType>> listAllNotDeleted() {
        return typeRepository.listAllNotDeleted();
    }
}
