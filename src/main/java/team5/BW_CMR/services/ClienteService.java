package team5.BW_CMR.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.exceptions.BadRequestException;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.exceptions.ValidationException;
import team5.BW_CMR.payloads.ClienteDTO;
import team5.BW_CMR.repositories.ClienteRepository;
import team5.BW_CMR.repositories.IndirizzoRepository;
import team5.BW_CMR.specifications.ClienteSpecifications;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private Cloudinary imageUploader;

    private static final long MAX_SIZE = 5 * 948 * 948;
    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpg",
            "image/png",
            "image/jpeg"
    );

    public Cliente save(ClienteDTO payload) {
        List<String> errors = new ArrayList<>();
        if (clienteRepository.existsByEmail(payload.email())) {
            errors.add("Email gia in uso!");
        }
        if (clienteRepository.existsByPartitaIva(Long.parseLong(payload.partitaIva()))) {
            errors.add("Partita IVA già in uso!");
        }
        if (clienteRepository.existsByPec(payload.pec())) {
            errors.add("Pec gia in uso!");
        }
        if (clienteRepository.existsByEmailContatto(payload.emailContatto())) {
            errors.add("Email del contatto gia in uso!");
        }
        if (clienteRepository.existsByTelefonoContatto(payload.telefonoContatto())) {
            errors.add("Numero telefono del contatto gia in uso!");
        }
        Indirizzo indirizzoLegale = indirizzoRepository.findById(payload.indirizzoLegaleId())
                .orElseThrow(() -> new ValidationException(List.of("Indirizzo legale non trovato")));

        Indirizzo indirizzoOperativo = indirizzoRepository.findById(payload.indirizzoOperativoId())
                .orElseThrow(() -> new ValidationException(List.of("Indirizzo operativo non trovato")));
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        Cliente cliente = new Cliente(
                Long.parseLong(payload.partitaIva()),
                payload.email(),
                payload.ragioneSociale(),
                payload.dataInserimento(),
                payload.dataUltimoContatto(),
                payload.fatturatoAnnuale(),
                payload.pec(),
                payload.telefono(),
                payload.emailContatto(),
                payload.nomeContatto(),
                payload.cognomeContatto(),
                payload.telefonoContatto(),
                indirizzoLegale,
                indirizzoOperativo
        );
        String emailPrefix = payload.email().split("@")[0];
        cliente.setLogoAziendale("https://ui-avatars.com/api/?name=" + emailPrefix);
        log.info("Cliente creato con successo: ");
        return clienteRepository.save(cliente);
    }

    public Cliente findById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente con id " + id + " non trovato"));
    }

    public Page<Cliente> findAll(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID id) {
        Cliente cliente = this.findById(id);
        clienteRepository.delete(cliente);
        log.info("Cliente eliminato con successo");
    }

    //FILTRA
    /*public Page<Cliente> findByParteNomeContatto(String parteNome, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findByParteNomeContatto(parteNome, pageable);
    }

    public Page<Cliente> findByFatturatoAnnuale(double fatturato, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findByFatturatoAnnuale(fatturato, pageable);
    }

    public Page<Cliente> findByDataInserimento(String data, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        LocalDate dataInserimento = LocalDate.parse(data);
        return clienteRepository.findByDataInserimento(dataInserimento, pageable);
    }

    public Page<Cliente> findByDataUltimoContatto(String data, int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        LocalDate dataUltimoContatto = LocalDate.parse(data);
        return clienteRepository.findByDataUltimoContatto(dataUltimoContatto, pageable);
    }

    //SOLO ORDINA
    public Page<Cliente> findAllOrderByDataUltimoContatto(int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataUltimoContatto").descending());
        return clienteRepository.findAll(pageable);
    }
    public Page<Cliente> findAllOrderByDataInserimento(int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by("dataInserimento").descending());
        return clienteRepository.findAll(pageable);
    }
    public Page<Cliente> findAllOrderByFatturatoAnnuale(int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by("fatturatoAnnuale").descending());
        return clienteRepository.findAll(pageable);
    }
    public Page<Cliente> findAllOrderByNomeContatto(int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by("nomeContatto").descending());
        return clienteRepository.findAll(pageable);
    }

    public  Page<Cliente> findAllOrderByProvincia(int page, int size) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size);
        return  clienteRepository.ordinaPerProvincia(pageable);
    }*/

    public Page<Cliente> findAllWithFilters(
            String nomeContatto,
            Double fatturato,
            LocalDate dataInserimento,
            LocalDate dataUltimoContatto,
            String provincia,
            int page,
            int size,
            String sortBy
    ) {
        if (size > 50) size = 50;
        Specification<Cliente> spec = (root, query, builder) -> builder.conjunction();
        if (nomeContatto != null && !nomeContatto.isEmpty()) {
            spec = spec.and(ClienteSpecifications.nomeContattoContiene(nomeContatto));
        }
        if (fatturato != null) {
            spec = spec.and(ClienteSpecifications.fatturatoUgualeA(fatturato));
        }
        if (dataInserimento != null) {
            spec = spec.and(ClienteSpecifications.dataInserimentoUgualeA(dataInserimento));
        }
        if (dataUltimoContatto != null) {
            spec = spec.and(ClienteSpecifications.dataUltimoContattoUgualeA(dataUltimoContatto));
        }
        if (provincia != null && !provincia.isEmpty()) {
            spec = spec.and(ClienteSpecifications.provinciaUgualeA(provincia));
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        return clienteRepository.findAll(spec, pageable);
    }


    //PATCH logo
    public Cliente uploadLogo(MultipartFile file, UUID id) {
        Cliente found = this.findById(id);
        if(file.isEmpty()) throw  new BadRequestException("File vuoto!");
        if(file.getSize() > MAX_SIZE) throw  new BadRequestException("Dimensioni troppo pesanti!");
        if(!ALLOWED_TYPES.contains(file.getContentType())) throw  new BadRequestException("Formato non valido! Solo JPG, JEPG, PNG");
        try {
            Map result = imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl= (String) result.get("url");
            found.setLogoAziendale(imageUrl);
        } catch ( IOException ex) {
            throw  new RuntimeException(ex);
        }
        this.clienteRepository.save(found);
        return found;
    }




}
