package com.example.foodapp.Model;

import java.util.List;

public class Post {
    private int id;
    private String title;
    private String description;
    private String postDate;
    private int price;
    private String address;
    private CategoryDTO categoryDTO;
    private List<PostImageDTO> postImageDTOs;

    private boolean published;


    public static class CategoryDTO {
        private int id;
        private String name;
        private ImageDTO imageDTO;

        public static class ImageDTO {
            private int id;
            private String name;
            private String url;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ImageDTO getImageDTO() {
            return imageDTO;
        }
    }

    public static class PostImageDTO {
        private int id;
        private ImageDTO imageDTO;
        private int postId;

        public static class ImageDTO {
            private int id;
            private String name;
            private String url;

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getUrl() {
                return url;
            }
        }

        public int getId() {
            return id;
        }

        public ImageDTO getImageDTO() {
            return imageDTO;
        }

        public int getPostId() {
            return postId;
        }
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPostDate() {
        return postDate;
    }

    public int getPrice() {
        return price;
    }

    public boolean getPublished() {
        return published;
    }

    public String getAddress() {
        return address;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public List<PostImageDTO> getPostImageDTOs() {
        return postImageDTOs;
    }
}



