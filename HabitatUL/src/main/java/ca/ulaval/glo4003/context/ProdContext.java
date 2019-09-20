package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.api.contact.ContactResource;
import ca.ulaval.glo4003.api.contact.ContactResourceImpl;
import ca.ulaval.glo4003.domain.contact.Contact;
import ca.ulaval.glo4003.domain.contact.ContactAssembler;
import ca.ulaval.glo4003.domain.contact.ContactRepository;
import ca.ulaval.glo4003.domain.contact.ContactService;
import ca.ulaval.glo4003.infrastructure.contact.ContactDevDataFactory;
import ca.ulaval.glo4003.infrastructure.contact.ContactRepositoryInMemory;

import java.util.List;

public class ProdContext implements Context {
  @Override
  public void execute() {
    ContactRepository contactRepository = new ContactRepositoryInMemory();
    ContactDevDataFactory contactDevDataFactory = new ContactDevDataFactory();
    List<Contact> contacts = contactDevDataFactory.createMockData();
    contacts.stream().forEach(contactRepository::save);

    ContactAssembler contactAssembler = new ContactAssembler();
    ContactService contactService = new ContactService(contactRepository, contactAssembler);

    ServiceLocator.register(ContactResource.class, new ContactResourceImpl(contactService));
  }
}
