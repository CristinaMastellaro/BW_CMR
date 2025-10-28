package team5.BW_CMR.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team5.BW_CMR.entities.Cliente;
import team5.BW_CMR.entities.Indirizzo;
import team5.BW_CMR.exceptions.NotFoundException;
import team5.BW_CMR.exceptions.ValidationException;
import team5.BW_CMR.payloads.ClienteDTO;
import team5.BW_CMR.repositories.ClienteRepository;
import team5.BW_CMR.repositories.IndirizzoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private IndirizzoRepository indirizzoRepository;

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

    public Page<Cliente> findByParteNomeContatto(String parteNome, int page, int size, String sortBy) {
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

}
