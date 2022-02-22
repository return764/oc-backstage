package com.oracleclub.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oracleclub.server.dao.BoardMapper;
import com.oracleclub.server.dao.PostMapper;
import com.oracleclub.server.entity.bbs.Board;
import com.oracleclub.server.entity.bbs.Post;
import com.oracleclub.server.entity.dto.PostTagDTO;
import com.oracleclub.server.entity.enums.UploadFileType;
import com.oracleclub.server.entity.param.PostParams;
import com.oracleclub.server.entity.support.UploadResult;
import com.oracleclub.server.entity.vo.PostVO;
import com.oracleclub.server.entity.vo.SimplePostVO;
import com.oracleclub.server.handler.file.FileHandlers;
import com.oracleclub.server.handler.file.support.PostImageUpload;
import com.oracleclub.server.service.PostService;
import com.oracleclub.server.service.base.AbstractCrudService;
import com.oracleclub.server.utils.ServiceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author :RETURN
 * @date :2021/10/10 23:13
 */
@Service
public class PostServiceImpl extends AbstractCrudService<Post,Long> implements PostService {

    private static final String HOME_ROUTER = "home";
    private final PostMapper postMapper;
    private final BoardMapper boardMapper;
    private final PostImageUpload postImageUpload;
    private final FileHandlers fileHandlers;

    protected PostServiceImpl(PostMapper postMapper, BoardMapper boardMapper, PostImageUpload postImageUpload, FileHandlers fileHandlers) {
        super(postMapper);
        this.postMapper = postMapper;
        this.boardMapper = boardMapper;
        this.postImageUpload = postImageUpload;
        this.fileHandlers = fileHandlers;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public IPage<SimplePostVO> pageBy(String boardRouterName, IPage<Post> pageable) {

        if (HOME_ROUTER.equals(boardRouterName)){
            return postMapper.pageAllBy(pageable);
        }

        Board board = boardMapper.findByRouterName(boardRouterName);

        if (Objects.isNull(board)){
            return pageable.convert(post -> new SimplePostVO().convertFrom(post));
        }
        return postMapper.pageBy(pageable,board.getId());
    }

    @Override
    public PostVO getHolePost(Long id) {
        return postMapper.getById(id);
    }

    @Override
    public String upload(MultipartFile image) {
        String uploadDir = postImageUpload.createUploadPath();
        UploadResult upload = fileHandlers.upload(image, UploadFileType.LOCAL,uploadDir);
        return ServiceUtils.changeFileSeparatorToUrlSeparator(upload.getFilePath());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createByParams(PostParams postParams) {
        Post post = new Post();
        Board board = boardMapper.findByName(postParams.getBoardName());
        Collection<String> tagIds = commaSeparatedStringToSet(postParams.getTags());

        post.setName(postParams.getName());
        post.setBoardId(board.getId());
        post.setCanComment(true);
        post.setIssuerId(postParams.getIssuerId());
        post.setContent(postParams.getContent());
        post.setLikeCount(0L);
        post.setTop(false);
        int i = postMapper.insert(post);
        pushPostWithTags(post, tagIds);

        if (i < 1){
            throw new RuntimeException("发帖失败");
        }
    }

    @Override
    public IPage<SimplePostVO> pageOwnPost(Long userId, IPage<Post> pageRequest) {
        return postMapper.pageByIssuer(pageRequest,userId);
    }

    private void pushPostWithTags(Post post, Collection<String> tagIds) {
        if (tagIds.size() == 0) {
            return;
        }

        List<PostTagDTO> lstTag = tagIds.stream()
                .map(tagId -> new PostTagDTO(post.getId(), Long.valueOf(tagId)))
                .collect(Collectors.toList());
        int j = postMapper.insertPostTagInBatch(lstTag);

        if (j < lstTag.size()) {
            throw new RuntimeException("发帖失败");
        }
    }
}
