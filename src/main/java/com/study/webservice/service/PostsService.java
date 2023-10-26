package com.study.webservice.service;

import com.study.webservice.domain.post.Posts;
import com.study.webservice.domain.post.PostsRepository;
import com.study.webservice.web.dto.PostsListResponseDto;
import com.study.webservice.web.dto.PostsResponseDto;
import com.study.webservice.web.dto.PostsSaveRequestDto;
import com.study.webservice.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {

        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }


    public PostsResponseDto findById(Long id) {

        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    //readOnly = true 옵션을 추가하면 트랜잭션 범위는 유지하되,
    //조회 기능만 남겨둬서 조회 속도가 개선된다
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {

        return postsRepository.findAllDesc().stream()
              //.map(posts -> new PostsListResponseDto(posts))
                .map(PostsListResponseDto::new)
                .toList();
    }

    @Transactional
    public void delete(Long id) {

        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }



}
