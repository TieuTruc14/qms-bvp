package qms.bvp.web.service.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import qms.bvp.common.DateUtils;
import qms.bvp.common.PagingResult;
import qms.bvp.config.Constants;
import qms.bvp.model.ReceptionArea;
import qms.bvp.model.User;
import qms.bvp.model.swap.AreaSwap;
import qms.bvp.web.repository.category.ReceptionAreaRepository;
import qms.bvp.web.service.logaccess.LogAccessService;
import qms.bvp.web.service.reception.ReceptionService;

import java.util.*;

/**
 * Created by Admin on 8/13/2018.
 */
@Service
public class ReceptionAreaServiceImpl implements ReceptionAreaService {
    @Autowired
    ReceptionAreaRepository areaRepository;
    @Autowired
    ReceptionService receptionService;
    @Autowired
    ReceptionDoorService doorService;
    @Autowired
    LogAccessService logAccessService;

    @Override
    public Optional<PagingResult> page(PagingResult page) {
        return areaRepository.page(page);
    }

    @Override
    public Optional<List<ReceptionArea>> listAll() {
        return Optional.of(areaRepository.findAll());
    }

    @Override
    public Optional<List<ReceptionArea>> listAllActive() {
        return areaRepository.listAllActive();
    }

    @Override
    public Optional<Hashtable<Integer, Integer>> initAreaWithOrderNumber(List<ReceptionArea> listAll) {
        Hashtable<Integer,Integer> mapNumberOfReceptionArea=new Hashtable<>();
        if(listAll==null || listAll.size()==0) return Optional.of(mapNumberOfReceptionArea);
        for(ReceptionArea item:listAll){
            mapNumberOfReceptionArea.put(item.getId(),0);
        }
        Date now=new Date();
        Date fromDate=null;
        Date toDate=null;
        try{
            String dateStr= DateUtils.dateToStr(now,"dd/MM/yyyy");
            fromDate= DateUtils.genDate(dateStr,true);
            toDate=DateUtils.genDate(dateStr,false);
        }catch (Exception e){

        }
        if(fromDate==null || toDate==null){
            return Optional.of(mapNumberOfReceptionArea);
        }
        List<Object[]> listObject=areaRepository.getMaxOrderNumberByArea(fromDate,toDate).orElse(new ArrayList<>());
        listObject.forEach(item->{
            Integer id=Integer.parseInt(item[0].toString());
            if(item[1]!=null){
                mapNumberOfReceptionArea.put(id,Integer.parseInt(item[1].toString()));
            }
        });
        return Optional.of(mapNumberOfReceptionArea);
    }

    @Override
    public Optional<List<ReceptionArea>> getByNameOrPrefix(String name, String prefix) {
        return areaRepository.getByNameOrPrefix(name,prefix);
    }

    @Override
    public Optional<Boolean> checkAreaByNameOrPrefix(String name, String prefix) {
        List<ReceptionArea> list=getByNameOrPrefix(name,prefix).orElse(null);
        if(list==null) return Optional.of(false);
        return Optional.of(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Byte> add(AreaSwap item,String ip) {
        if(item==null) return Optional.of(Byte.valueOf("3"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionArea ra=new ReceptionArea();
        ra.setName(item.getName());
        ra.setPrefix(item.getPrefix());
        ra.setDate_created(new Date());
        ra.setUser_created(user.getId());
        ra.setDisable(false);
        ra.setDeleted(false);
        ra.setLoudspeaker_times(item.getLoudspeaker_times());
        ra.setDescription(item.getDescription());
        areaRepository.save(ra);
        logAccessService.addLog(Constants.ActionLog.Add,"Thêm khu vực khám","Danh mục",Constants.TableName.ReceptionArea,ra.getId().toString(),ra.toString(),ip);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Byte> edit(AreaSwap item,String ip) {
        if(item==null) return Optional.of(Byte.valueOf("3"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionArea itemDB=areaRepository.findById(item.getId()).orElse(null);
        if(itemDB==null) return Optional.of(Byte.valueOf("3"));
        itemDB.setName(item.getName());
        itemDB.setPrefix(item.getPrefix());
        itemDB.setDate_updated(new Date());
        itemDB.setUser_updated(user.getId());
        itemDB.setDisable(false);
        itemDB.setDeleted(false);
        itemDB.setLoudspeaker_times(item.getLoudspeaker_times());
        itemDB.setDescription(item.getDescription());
        areaRepository.save(itemDB);
        logAccessService.addLog(Constants.ActionLog.Edit,"Sửa khu vực khám","Danh mục",Constants.TableName.ReceptionArea,itemDB.getId().toString(),itemDB.toString(),ip);
        return Optional.of(Byte.valueOf("1"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Byte> delete(Integer id,String ip) {
        if(id==null) return  Optional.of(Byte.valueOf("3"));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user==null) return Optional.of(Byte.valueOf("2"));
        ReceptionArea itemDB=areaRepository.findById(id).orElse(null);
        if(itemDB==null) return Optional.of(Byte.valueOf("3"));
        if((new Date()).getTime()-itemDB.getDate_created().getTime()>86400000){
            return Optional.of(Byte.valueOf("5"));//ko cho xoa khi da tao qua 1 ngay
        }
        Long count=receptionService.countReceptionByArea(id).orElse(Long.valueOf("0"));
        if(count.longValue()>0) return Optional.of(Byte.valueOf("5"));
        doorService.deleteAllDoorOfArea(id,user.getId(),new Date());
        itemDB.setDeleted(true);
        itemDB.setUser_updated(user.getId());
        itemDB.setDate_updated(new Date());
        areaRepository.save(itemDB);
        logAccessService.addLog(Constants.ActionLog.Delete,"Xóa khu vực khám","Danh mục",Constants.TableName.ReceptionArea,itemDB.getId().toString(),itemDB.toString(),ip);
        return Optional.of(Byte.valueOf("1"));
    }
}
