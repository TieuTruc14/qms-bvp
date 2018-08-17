package qms.bvp.web.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qms.bvp.common.PagingResult;
import qms.bvp.model.ReceptionArea;
import qms.bvp.web.repository.category.ReceptionAreaRepository;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Admin on 8/13/2018.
 */
@Service
public class ReceptionAreaServiceImpl implements ReceptionAreaService {
    @Autowired
    ReceptionAreaRepository areaRepository;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        return areaRepository.page(page);
    }

    @Override
    public Optional<List<ReceptionArea>> listAll() {
        return Optional.of(areaRepository.findAll());
    }

    @Override
    public Optional<Hashtable<Integer, Integer>> initAreaWithOrderNumber() {
        Hashtable<Integer,Integer> mapNumberOfReceptionArea=new Hashtable<>();
        List<ReceptionArea> listAll=areaRepository.findAll();
        if(listAll==null || listAll.size()==0) return Optional.of(mapNumberOfReceptionArea);
        for(ReceptionArea item:listAll){
            mapNumberOfReceptionArea.put(item.getId(),0);
        }
        List<Object[]> listObject=areaRepository.getMaxOrderNumberByArea().orElse(new ArrayList<>());
        for(Object[] item:listObject){
            Integer id=Integer.parseInt(item[0].toString());
            if(item[1]!=null){
                mapNumberOfReceptionArea.put(id,Integer.parseInt(item[1].toString()));
            }
        }
        return Optional.of(mapNumberOfReceptionArea);
    }
}
