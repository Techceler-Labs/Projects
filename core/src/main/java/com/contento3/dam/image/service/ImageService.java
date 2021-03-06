package com.contento3.dam.image.service;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.common.service.StorableService;
import com.contento3.dam.image.dto.ImageDto;

public interface ImageService extends StorableService<ImageDto> {

	/**
	 * Finds image by uuid
	 * @param imageId
	 * @return ImageDto
	 */
	ImageDto findImageByUuid(String imageId);

	/**
	 * Returns {@link java.util.Collection}} an image by accountId
	 * @param accountId
	 * @return ImageDto
	 */
	Collection<ImageDto> findImageByAccountId(Integer accountId);

	/**
	 * Returns an image by name and accountId.
	 * @param name
	 * @param accountId
	 * @return ImageDto
	 * @throws EntityNotFoundException 
	 */
	ImageDto findImageByNameAndAccountId(String name,
			Integer accountId) throws EntityNotFoundException;
	
	/**
	 * Returns latest images by account id and count is number of rows 
	 * @param siteId
	 * @param count
	 * @return
	 */
	Collection<ImageDto> findLatestImagesBySiteId(Integer siteId,Integer count);
	
	/**
	 * Returns image by siteId
	 * @param imageId TODO
	 * @param siteId
	 * @param count
	 * @return
	 */
	ImageDto findImageByIdAndSiteId(Integer imageId, Integer siteId);
	
	
	/**
	 * Return a {@link java.util.Collection} of image by library
	 * @param imageId
	 * @return
	 */
	Collection<ImageDto> findImagesByLibrary(Integer libraryId);

	
    /**
     * Create new image
     * @param categoryDto
     * @return
     */
	Boolean create(ImageDto imageDto) throws EntityAlreadyFoundException;
	
	/**
	 * Update image 
	 * @param imageDto
	 */
	void update(final ImageDto imageDto);
	
	/**
	 * Return image by id
	 * @param imageId
	 * @return
	 */
	ImageDto findById(Integer imageId);

	/**
	 * Used to crop the image
	 * @param imageToCrop
	 * @return
	 */
	ImageDto crop(ImageDto imageToCrop);
	
	/**
	 * Used to resize the image
	 * @param imageToResize
	 * @return
	 */
	ImageDto resize(ImageDto imageToResize);
	
	/**
	 * Used to rotate the image
	 * @param imageToRotate
	 * @return
	 */
	ImageDto rotate(ImageDto imageToRotate);
	
	/**
	 * Returns collection of images by filtering using accountId and libraryId 
	 * @param libraryId
	 * @param accountId
	 * @return
	 */
	Collection<ImageDto> findImagesByLibraryAndAccountId(Integer libraryId, Integer accountId);
}



