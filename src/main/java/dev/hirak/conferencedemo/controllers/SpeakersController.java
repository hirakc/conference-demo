package dev.hirak.conferencedemo.controllers;

import dev.hirak.conferencedemo.models.Speaker;
import dev.hirak.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRepository.getOne(id);
    }

    @PostMapping
    public Speaker create(@RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        // Also need to check for children methods before deleting
        speakerRepository.deleteById(id);
    }

    @RequestMapping( value="{id}", method=RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        //because this is Put we expect all attributes to be passed in. A patch would be needed if we want to update only changed fields
        //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Speaker existingSpeaker  = speakerRepository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker,  "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

}
