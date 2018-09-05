package com.mng.hermes.service;

import com.mng.hermes.dto.MessageDTO;
import com.mng.hermes.model.TargetType;
import com.mng.hermes.model.User;
import com.mng.hermes.repository.MessageRepository;
import com.mng.hermes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public List<MessageDTO> getMessages(String targetAddress, TargetType type, int ownerId) {
        if (targetAddress == null && type == null) {
            return messageRepository.findAllByTargetAddressIsNullAndTypeIsNull().stream()
                    .map(MessageDTO::construct)
                    .collect(Collectors.toList());
        }
        if (type.equals(TargetType.ROOM)) {
            return messageRepository.findAllByTargetAddressEquals(targetAddress).stream()
                    .map(MessageDTO::construct)
                    .collect(Collectors.toList());
        } else {
            User owner = userRepository.findById(ownerId).orElse(null);
            User target = userRepository.findById(Integer.valueOf(targetAddress)).orElse(null);
            List<MessageDTO> received = messageRepository.findAllByTargetAddressAndOwner(String.valueOf(ownerId), target).stream()
                    .map(MessageDTO::construct)
                    .collect(Collectors.toList());
            received.addAll(messageRepository.findAllByTargetAddressAndOwner(targetAddress, owner).stream()
                    .map(MessageDTO::construct)
                    .collect(Collectors.toList()));
            return received;
        }
    }
}
