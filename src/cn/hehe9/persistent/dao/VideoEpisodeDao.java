package cn.hehe9.persistent.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.ws.RespectBinding;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.hehe9.persistent.entity.VideoEpisode;
import cn.hehe9.persistent.mapper.VideoEpisodeMapper;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class VideoEpisodeDao {

	@Resource
	private VideoEpisodeMapper videoEpisodeMapper;

	public int save(VideoEpisode ve) {
		ve.setCreateTime(new Date());
		return videoEpisodeMapper.insertSelective(ve);
	}

	public VideoEpisode findByVideoIdEpisodeNo(Integer videoId, Integer episodeNo) {
		List<VideoEpisode> episodes = findBy(videoId, episodeNo, 1, 1);
		return CollectionUtils.isEmpty(episodes) ? null : episodes.get(0);
	}
	
	public List<VideoEpisode> findEpisodesBy(Integer videoId, int page, int pageCount) {
		return findBy(videoId, null, page, pageCount);
	}

	/**
	 * 根据条件, 查询视频信息
	 * @param episodeNo	集数
	 * @param page	查询页码
	 * @param count	查询数量
	 * @return
	 */
	public List<VideoEpisode> findBy(Integer videoId, Integer episodeNo, int page, int pageCount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("videoId", videoId);
		params.put("episodeNo", episodeNo);
		params.put("offset", (page - 1) * pageCount);
		params.put("count", pageCount);
		List<VideoEpisode> result = videoEpisodeMapper.findBy(params);
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result;
	}

	public int udpate(VideoEpisode ve) {
		return videoEpisodeMapper.updateByPrimaryKeySelective(ve);
	}
}
